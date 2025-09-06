package dev.gerardomarquez.chat.srvices;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.chat.configurations.JwtConfiguration;
import dev.gerardomarquez.chat.enitities.UserEntity;
import dev.gerardomarquez.chat.repositories.ConversationRequestRepository;
import dev.gerardomarquez.chat.repositories.UsersRepository;
import dev.gerardomarquez.chat.requests.InsertRequestConversatinRequest;
import dev.gerardomarquez.chat.responses.GenericResponse;

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
     * Clase que se conecta a la base de datos para los request de conversation
     */
    private final ConversationRequestRepository conversationRequestRepository;

    /*
     * Clase que se conecta a la base de datos para los request de conversation
     */
    private final UsersRepository usersRepository;

    /*
     * Constructor que inyecta las variables con spring
     * @param conversationRequestRepository Clase que se conecta a la base de datos para los request de conversation
     * @param jwtConfiguraion Clase que genera y deserializa el token
     */
    public ConversationRequestServiceImplementation(
        ConversationRequestRepository conversationRequestRepository,
        JwtConfiguration jwtConfiguration,
        UsersRepository usersRepository
    ){
        this.conversationRequestRepository = conversationRequestRepository;
        this.jwtConfiguration = jwtConfiguration;
        this.usersRepository = usersRepository;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public GenericResponse insertOneRequestConversation(InsertRequestConversatinRequest requst, String token) {
        String requestedUserName = jwtConfiguration.extractUsername(token);
        String targetUsernName = requst.getUserNameTarget();

        Optional<UserEntity> optionalRequestedEntity = usersRepository.findByUsername(requestedUserName);
        Optional<UserEntity> optionalTargetEntity = usersRepository.findByUsername(targetUsernName);

        if(optionalTargetEntity.isEmpty() ){
            throw new IllegalArgumentException("");
        }

        conversationRequestRepository.findAllByRequesterAndTarget(optionalRequestedEntity.get(), optionalTargetEntity.get() );


        return null;
    }

}
