package dev.gerardomarquez.chat.srvices;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.chat.configurations.JwtConfiguration;
import dev.gerardomarquez.chat.enitities.ConversationRequestEntity;
import dev.gerardomarquez.chat.enitities.UserEntity;
import dev.gerardomarquez.chat.exceptions.SameUserToRequestException;
import dev.gerardomarquez.chat.exceptions.TooManyConversationRequestsException;
import dev.gerardomarquez.chat.jms.ConversationRequestJmsI;
import dev.gerardomarquez.chat.repositories.ConversationRequestRepository;
import dev.gerardomarquez.chat.repositories.UsersRepository;
import dev.gerardomarquez.chat.requests.InsertRequestConversatinRequest;
import dev.gerardomarquez.chat.requests.SendQueueConversationRequest;
import dev.gerardomarquez.chat.responses.GenericResponse;
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
     * Clase para enviar la el mensaje a la cola JMS del las peticiones de conversacion
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

        if(areTherePendings){
            throw new TooManyConversationRequestsException(
                messageSource.getMessage(
                    Constants.MSG_EXCEPTION_REQUEST_CONVERSATION_PENDING_FOUND,
                    null,
                    Locale.getDefault()
                )
            );
        }

        Long countRejected = listConversationRequest.stream()
            .filter(it -> it.getStatus().equals(Constants.CONVERSATION_REQUEST_STATUS_THREE) )
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

        LocalDateTime now = LocalDateTime.now();

        ConversationRequestEntity conversationRequestEntity = ConversationRequestEntity.builder()
            .createdAt(now)
            .requester(optionalRequestedEntity.get() )
            .target(optionalTargetEntity.get() )
            .status(Constants.CONVERSATION_REQUEST_STATUS_ONE)
            .build();

        conversationRequestRepository.save(conversationRequestEntity);

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

        conversationRequestJms.sendQueueToUsuer(sendQueueRequest, optionalTargetEntity.get().getId().toString() );

        GenericResponse response = GenericResponse.builder()
            .success(Boolean.TRUE)
            .message(messageSource.getMessage(Constants.MSG_SUCCESS, null, Locale.getDefault() ) )
            .data(messageSource.getMessage(
                    Constants.MSG_REQUEST_CONVERSATION_RESPONSE_SUCCESS,
                    null,
                    Locale.getDefault() 
                )
            )
            .build();

        return response;
    }

}
