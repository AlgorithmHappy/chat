package dev.gerardomarquez.chat.srvices;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.chat.enitities.UserEntity;
import dev.gerardomarquez.chat.repositories.UsersRepository;
import dev.gerardomarquez.chat.requests.InsertUserRequest;
import dev.gerardomarquez.chat.responses.GenericResponse;
import dev.gerardomarquez.chat.responses.UserCreatedResponse;
import dev.gerardomarquez.chat.utils.Constants;

/*
 * {@inheritDoc}
 */
@Service
public class UsersManagerServiceImplementation implements UsersManagerServiceI{

    /*
     * Repositorio para los usuarios
     */
    private final UsersRepository usersRepository;

    /*
     * Objeto de spring para hashear la contraseña
     */
    private final PasswordEncoder passwordEncoder;

    /*
     * Objeto que gestiona los mensajes
     */
    private final MessageSource messageSource;

    /*
     * Constructor que inyecta los atributos con spring
     * @param usersRepository Repositorio para los usuarios
     * @param passwordEncoder Objeto de spring para hashear la contraseña
     */
    public UsersManagerServiceImplementation(
        UsersRepository usersRepository,
        PasswordEncoder passwordEncoder,
        MessageSource messageSource
    ){
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public GenericResponse insertOneUser(InsertUserRequest insertUserRequest) {
        UserEntity userEntity = UserEntity.builder()
            .username(insertUserRequest.getNickName() )
            .passwordHash(passwordEncoder.encode(insertUserRequest.getPassword() ) )
            .isActive(Boolean.FALSE)
            .createdAt(LocalDateTime.now() )
            .build();

        usersRepository.save(userEntity);

        UserCreatedResponse userCreatedResponse = UserCreatedResponse.builder()
            .createdAt(userEntity.getCreatedAt() )
            .nickName(userEntity.getUsername() )
            .build();

        GenericResponse genericResponse = GenericResponse.builder()
            .success(Boolean.TRUE)
            .message(messageSource.getMessage(Constants.MSG_SUCCESS, null, Locale.getDefault() ) )
            .data(userCreatedResponse)
            .build();

        return genericResponse;
    }
}
