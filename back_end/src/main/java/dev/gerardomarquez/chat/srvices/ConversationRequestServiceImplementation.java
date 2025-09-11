package dev.gerardomarquez.chat.srvices;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.chat.configurations.JwtConfiguration;
import dev.gerardomarquez.chat.enitities.ConversationRequestEntity;
import dev.gerardomarquez.chat.enitities.UserEntity;
import dev.gerardomarquez.chat.exceptions.RequestConversationNotFoundException;
import dev.gerardomarquez.chat.exceptions.RequestConversationStatusUnauthorizedException;
import dev.gerardomarquez.chat.exceptions.RequestConversationUserUnauthorizedException;
import dev.gerardomarquez.chat.exceptions.SameUserToRequestException;
import dev.gerardomarquez.chat.exceptions.TooManyConversationRequestsException;
import dev.gerardomarquez.chat.jms.ConversationRequestJmsI;
import dev.gerardomarquez.chat.repositories.ConversationRequestRepository;
import dev.gerardomarquez.chat.repositories.UsersRepository;
import dev.gerardomarquez.chat.requests.DeleteConversationToTarget;
import dev.gerardomarquez.chat.requests.InsertRequestConversatinRequest;
import dev.gerardomarquez.chat.requests.SendQueueConversationRequest;
import dev.gerardomarquez.chat.responses.GenericResponse;
import dev.gerardomarquez.chat.responses.RequestConversationCreatedResponse;
import dev.gerardomarquez.chat.utils.Constants;

/*
 * {@inheritDoc}
 */
@Service
public class ConversationRequestServiceImplementation implements ConversationRequestServiceI {

    /*
     * Clase que genera y deserializa el token
     */
    private final JwtConfiguration jwtConfiguration;

    /*
     * Clase para enviar el mensaje a la cola JMS del las peticiones de conversacion
     */
    private final ConversationRequestJmsI conversationRequestJms;

    /*
     * Clase que se conecta a la base de datos para los request de conversation
     */
    private final ConversationRequestRepository conversationRequestRepository;

    /*
     * Clase que se conecta a la base de datos para gestionar los usuarios
     */
    private final UsersRepository usersRepository;

    /*
     * Objeto que gestiona los mensajes
     */
    private final MessageSource messageSource;

    @Value("${jwt.hours.time}")
    private Integer hoursSession;

    /*
     * Constructor que inyecta las variables con spring
     * @param conversationRequestRepository Clase que se conecta a la base de datos para los request de conversation
     * @param jwtConfiguraion Clase que genera y deserializa el token
     * @param usersRepository Clase que se conecta a la base de datos para gestionar los usuarios
     * @param conversationRequestJms Clase para enviar la el mensaje a la cola JMS del las peticiones de conversacion
     * @param messageSource Objeto que gestiona los mensajes
     */
    public ConversationRequestServiceImplementation(
        ConversationRequestRepository conversationRequestRepository,
        JwtConfiguration jwtConfiguration,
        UsersRepository usersRepository,
        ConversationRequestJmsI conversationRequestJms,
        MessageSource messageSource
    ){
        this.conversationRequestRepository = conversationRequestRepository;
        this.jwtConfiguration = jwtConfiguration;
        this.usersRepository = usersRepository;
        this.messageSource = messageSource;
        this.conversationRequestJms = conversationRequestJms;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public GenericResponse insertOneRequestConversation(InsertRequestConversatinRequest requst, String token) {
        String requestedUserName = jwtConfiguration.extractUsername(token);
        String targetUsernName = requst.getUserNameTarget();

        if(requestedUserName.equals(targetUsernName) ){
            throw new SameUserToRequestException(
                messageSource.getMessage(
                    Constants.MSG_REQUEST_CONVERSATION_SAME_USER,
                    null,
                    Locale.getDefault()
                )
            );
        }

        Optional<UserEntity> optionalRequestedEntity = usersRepository.findByUsername(requestedUserName);
        Optional<UserEntity> optionalTargetEntity = usersRepository.findByUsername(targetUsernName);

        if(optionalTargetEntity.isEmpty() ){
            throw new IllegalArgumentException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_NAME_NOT_FOUND,
                    new Object[] { targetUsernName },
                    Locale.getDefault()
                )
            );
        }

        List<ConversationRequestEntity> listConversationRequest = conversationRequestRepository
            .findAllByRequesterAndTarget(optionalRequestedEntity.get(), optionalTargetEntity.get() );

        Boolean areTherePendings = listConversationRequest.stream()
            .anyMatch(it -> it.getStatus().equals(Constants.CONVERSATION_REQUEST_STATUS_ONE ) );

        Boolean areThereReceived = listConversationRequest.stream()
            .anyMatch(it -> it.getStatus().equals(Constants.CONVERSATION_REQUEST_STATUS_TWO ) );

        Boolean areThereAcepted = listConversationRequest.stream()
            .anyMatch(it -> it.getStatus().equals(Constants.CONVERSATION_REQUEST_STATUS_THREE ) );

        if(areTherePendings){
            throw new TooManyConversationRequestsException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_FOUND,
                    new Object[]{ Constants.MSG_CONVERSATION_REQUEST_STATUS_ONE },
                    Locale.getDefault()
                )
            );
        }
        if(areThereReceived){
            throw new TooManyConversationRequestsException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_FOUND,
                    new Object[]{ Constants.MSG_CONVERSATION_REQUEST_STATUS_TWO },
                    Locale.getDefault()
                )
            );
        }
        if(areThereAcepted){
            throw new TooManyConversationRequestsException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_FOUND,
                    new Object[]{ Constants.MSG_CONVERSATION_REQUEST_STATUS_THREE },
                    Locale.getDefault()
                )
            );
        }

        Long countRejected = listConversationRequest.stream()
            .filter(it -> it.getStatus().equals(Constants.CONVERSATION_REQUEST_STATUS_FOUR) )
            .count();

        if(countRejected > Constants.CONVERSATION_REQUEST_LIMIT){
            throw new TooManyConversationRequestsException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_LIMIT_REJECTED,
                    null,
                    Locale.getDefault()
                )
            );
        }

        /*
         * Validar si hay una request de conversacion a la inversa, ya que no puede mandar una peticion
         * de conversacion si ya la recivio antes
         */
        List<ConversationRequestEntity> listConversationRequestInverse = conversationRequestRepository
            .findAllByRequesterAndTarget(optionalTargetEntity.get(), optionalRequestedEntity.get() );
        areTherePendings = listConversationRequestInverse.stream()
            .anyMatch(it -> it.getStatus().equals(Constants.CONVERSATION_REQUEST_STATUS_ONE ) );
        areThereAcepted = listConversationRequestInverse.stream()
            .anyMatch(it -> it.getStatus().equals(Constants.CONVERSATION_REQUEST_STATUS_TWO ) );
        areThereReceived = listConversationRequestInverse.stream()
            .anyMatch(it -> it.getStatus().equals(Constants.CONVERSATION_REQUEST_STATUS_TWO ) );
        if(areTherePendings){
            throw new TooManyConversationRequestsException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_FOUND_REVERSE,
                    new Object[]{ Constants.MSG_CONVERSATION_REQUEST_STATUS_ONE },
                    Locale.getDefault()
                )
            );
        }
        if(areThereReceived){
            throw new TooManyConversationRequestsException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_FOUND_REVERSE,
                    new Object[]{ Constants.MSG_CONVERSATION_REQUEST_STATUS_TWO },
                    Locale.getDefault()
                )
            );
        }
        if(areThereAcepted){
            throw new TooManyConversationRequestsException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_FOUND_REVERSE,
                    new Object[]{ Constants.MSG_CONVERSATION_REQUEST_STATUS_THREE },
                    Locale.getDefault()
                )
            );
        }
        /*
         * Fin
         */

        LocalDateTime now = LocalDateTime.now();

        ConversationRequestEntity conversationRequestEntity = ConversationRequestEntity.builder()
            .createdAt(now)
            .requester(optionalRequestedEntity.get() )
            .target(optionalTargetEntity.get() )
            .status(Constants.CONVERSATION_REQUEST_STATUS_ONE)
            .build();

        conversationRequestRepository.save(conversationRequestEntity);

        if(
            optionalTargetEntity.get().getLastLogin() != null
        ){
            LocalDateTime sessionExpired = optionalTargetEntity.get().getLastLogin().plusHours(hoursSession);
            if(LocalDateTime.now().isBefore(sessionExpired) ){ // Mandar mensaje asincrono en el caso de que
                //este conectado ahora, si no esperar a que se conecte y obtene las peticiones de forma sincrona
                SendQueueConversationRequest sendQueueRequest = SendQueueConversationRequest.builder()
                    .message(
                        messageSource.getMessage(
                            Constants.MSG_REQUEST_CONVERSATION_MESSAGE,
                            new Object[] {requestedUserName},
                            Locale.getDefault()
                        )
                    )
                    .date(now)
                    .fromUserName(requestedUserName)
                    .build();

                conversationRequestJms.sendRequestConversationQueueToUsuer(sendQueueRequest, optionalTargetEntity.get().getId().toString() );
            }
        }

        RequestConversationCreatedResponse requestConversationCreatedResponse = new RequestConversationCreatedResponse(
            optionalTargetEntity.get().getUsername(),
            conversationRequestEntity.getStatus(),
            conversationRequestEntity.getCreatedAt(),
            conversationRequestEntity.getId()
        );

        GenericResponse response = GenericResponse.builder()
            .success(Boolean.TRUE)
            .message(messageSource.getMessage(Constants.MSG_SUCCESS, null, Locale.getDefault() ) )
            .data(requestConversationCreatedResponse)
            .build();

        return response;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public GenericResponse getAllRequestConversation(String token) {
        String requestedUserName = jwtConfiguration.extractUsername(token);

        UserEntity userEntity = usersRepository.findByUsername(requestedUserName)
            .orElseThrow(() -> new UsernameNotFoundException(
                messageSource.getMessage(Constants.MSG_EXCEPTION_USERNAME_NOT_FOUND, new Object[]{ requestedUserName },  Locale.getDefault() )
            ) 
        );

        List<ConversationRequestEntity> listConversationEntities = conversationRequestRepository.findAllByRequester(userEntity);

        List<RequestConversationCreatedResponse> listRequestConversations = listConversationEntities.stream()
            .map(
                it -> {
                    RequestConversationCreatedResponse requestConversationCreatedResponse = new RequestConversationCreatedResponse(
                        it.getTarget().getUsername(),
                        it.getStatus(),
                        it.getCreatedAt(),
                        it.getId()
                    );
                    return requestConversationCreatedResponse;
                }
            )
            .collect(Collectors.toList() );

        GenericResponse response = new GenericResponse(
            Boolean.TRUE,
            messageSource.getMessage(Constants.MSG_SUCCESS, null, Locale.getDefault() ),
            listRequestConversations
        );

        return response;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public void deleteOneRequestConversation(String token, String requestConversationId) {
        String requestedUserName = jwtConfiguration.extractUsername(token);
        Optional<ConversationRequestEntity> optConversationRequest = conversationRequestRepository
            .findById(UUID.fromString(requestConversationId) );

        if(optConversationRequest.isEmpty() ){
            throw new RequestConversationNotFoundException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_NOT_FOUND, 
                    null,
                    Locale.getDefault()   
                )
            );
        }

        UserEntity userRequester = optConversationRequest.get().getRequester();

        if(!userRequester.getUsername().equals(requestedUserName) ){
            throw new RequestConversationUserUnauthorizedException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_UNAUTHORIZED_USER, 
                    null,
                    Locale.getDefault()
                )
            );
        }

        if(
            !(
                optConversationRequest.get().getStatus().equals(Constants.CONVERSATION_REQUEST_STATUS_ONE ) ||
                optConversationRequest.get().getStatus().equals(Constants.CONVERSATION_REQUEST_STATUS_TWO)
            )
        ){
            throw new RequestConversationStatusUnauthorizedException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_UNAUTHORIZED_STATUS, 
                    null,
                    Locale.getDefault()
                )
            );
        }

        conversationRequestRepository.delete(optConversationRequest.get() );

        if(optConversationRequest.get().getTarget().getLastLogin() != null){
            if(optConversationRequest.get().getTarget().getLastLogin().plusHours(hoursSession).isBefore(LocalDateTime.now() ) ){
                DeleteConversationToTarget deleteConversationToTarget = new DeleteConversationToTarget(
                    optConversationRequest.get().getId().toString()
                );

                conversationRequestJms.sendRequestConversationQueueToUsuer(
                    deleteConversationToTarget,
                    optConversationRequest.get().getTarget().getId().toString()
                );
            }
        }
    }

}
