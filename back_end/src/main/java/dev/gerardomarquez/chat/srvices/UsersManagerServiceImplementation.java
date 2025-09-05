package dev.gerardomarquez.chat.srvices;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.chat.configurations.JwtConfiguration;
import dev.gerardomarquez.chat.enitities.UserEntity;
import dev.gerardomarquez.chat.exceptions.UserAlreadyLoggedInException;
import dev.gerardomarquez.chat.repositories.UsersRepository;
import dev.gerardomarquez.chat.requests.InsertUserRequest;
import dev.gerardomarquez.chat.responses.GenericResponse;
import dev.gerardomarquez.chat.responses.UserCreatedResponse;
import dev.gerardomarquez.chat.utils.Constants;

/*
 * {@inheritDoc}
 */
@Service
public class UsersManagerServiceImplementation implements UsersManagerServiceI, UserDetailsService{

    /*
     * Repositorio para los usuarios
     */
    private final UsersRepository usersRepository;

    /*
     * Objeto de spring para hashear la contraseña
     */
    private final PasswordEncoder passwordEncoder;

    /*
     * Objeto para deserializar el token o crear el token
     */
    private final JwtConfiguration jwtConfiguration;

    /*
     * Objeto que gestiona los mensajes
     */
    private final MessageSource messageSource;

    @Value("${jwt.hours.time}")
    private Integer hoursSession;

    /*
     * Constructor que inyecta los atributos con spring
     * @param usersRepository Repositorio para los usuarios
     * @param passwordEncoder Objeto de spring para hashear la contraseña
     * @param messageSource Gestor de mensajes de error o informacion
     * @param jwtConfiguration Clase para crear o deserializar el token
     */
    public UsersManagerServiceImplementation(
        UsersRepository usersRepository,
        PasswordEncoder passwordEncoder,
        MessageSource messageSource,
        JwtConfiguration jwtConfiguration
    ){
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
        this.jwtConfiguration = jwtConfiguration;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public GenericResponse insertOneUser(InsertUserRequest insertUserRequest) {
        UserEntity userEntity = UserEntity.builder()
            .username(insertUserRequest.getNickName() )
            .passwordHash(passwordEncoder.encode(insertUserRequest.getPassword() ) )
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

    /*
    * {@inheritDoc}
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = usersRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                messageSource.getMessage(Constants.MSG_EXCEPTION_USERNAME_NOT_FOUND, new Object[]{ username },  Locale.getDefault() )
            ) 
        );

        if(userEntity.getLastLogin() != null){
            LocalDateTime sessionExpired = userEntity.getLastLogin().plusHours(hoursSession);
            if(LocalDateTime.now().isBefore(sessionExpired) ){
                Duration duration = Duration.between(LocalDateTime.now(), sessionExpired);
                throw new UserAlreadyLoggedInException(
                    messageSource.getMessage(
                        Constants.MSG_EXCEPTION_USER_ALREADY_LOGGED,
                        new Object[]{ username, duration.toMinutes() },
                        Locale.getDefault()
                    )
                );
            }
        }

        userEntity.setLastLogin(LocalDateTime.now() );
        usersRepository.save(userEntity);

        return User.withUsername(userEntity.getUsername() )
                   .password(userEntity.getPasswordHash() )
                   .authorities(Collections.emptyList() ) // o roles en lista
                   .build();
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public void resetDateByUsuer(String token) {
        String user = jwtConfiguration.extractUsername(token);
        Optional<UserEntity> optionalUserEntity = usersRepository.findByUsername(user);

        if(optionalUserEntity.isPresent() ){
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setLastLogin(null);
            usersRepository.save(userEntity);
        }
    }
}
