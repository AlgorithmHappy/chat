package dev.gerardomarquez.chat.exceptions;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.gerardomarquez.chat.responses.GenericResponse;
import dev.gerardomarquez.chat.utils.Constants;
import lombok.extern.log4j.Log4j2;

/*
 * Gestiona las excepciones
 */
@RestControllerAdvice
@Log4j2
public class ErrorHandlerException {

    @Value("${rate.limit.refill.minutes}")
    private String minutes;

    /*
     * Mensajes de error
     */
    @Autowired
    private MessageSource messageSource;

    /*
     * Metodo que controla la excepcion de las anotaciones de validacion de jakarta
     * @param MethodArgumentNotValidException excepcion que arroja jakarta y que controlamos aqui.
     * @return Response generico con los errores
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse> handleValidationError(MethodArgumentNotValidException ex){

        List<String> listErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map( it -> it.getDefaultMessage() )
            .collect(Collectors.toList() );

        GenericResponse genericResponse = GenericResponse.builder()
            .success(Boolean.FALSE)
            .message(ex.getMessage() )
            .data(listErrors)
            .build();
            
        log.error(genericResponse );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(genericResponse);
    }

    /*
     * Metodo que controla la excepcion de los atributos unicos en la base de datos
     * @param DataIntegrityViolationException excepcion que arroja spring data jpa que indica que se esta repitiendo el atributo.
     * @return Response generico con los errores
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GenericResponse> handleDuplicateKey(DataIntegrityViolationException ex) {
        GenericResponse response = GenericResponse.builder()
            .message(ex.getMessage() )
            .success(Boolean.FALSE)
            .data(messageSource.getMessage(Constants.MSG_DB_CONTRAINT_UNIQUE_VIOLATED, null, Locale.getDefault() ) )
            .build();

        log.error(response );

        return ResponseEntity.unprocessableEntity().body(response);
    }

    /*
     * Metodo que controla la excepcion del repositorio al no encontrar al usuario con el nombre de usuario dado
     * @param UsernameNotFoundException excepcion que arroja optinal personalizadamente para indicar que el usuario no esta presente
     * @return Response generico con los errores
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GenericResponse> handleDuplicateKey(UsernameNotFoundException ex) {
        GenericResponse response = GenericResponse.builder()
            .message(ex.getMessage() )
            .success(Boolean.FALSE)
            .data(ex.getMessage() )
            .build();

        log.error(response );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /*
     * Metodo que controla la excepcion de credenciales invalidas
     * @param AuthenticationException excepcion que arroja spring cuando las credenciales son invalidas
     * @return Response generico con los errores
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GenericResponse> handleAuthenticationException(AuthenticationException ex) {

        /*
         * Si la excepcion es porque ya se logeo anteriormente
         */
        if(ex.getCause() instanceof UserAlreadyLoggedInException){
            UserAlreadyLoggedInException exception = new UserAlreadyLoggedInException(ex.getMessage() );
            return this.handleUserAlreadyLogged(exception );
        }

        GenericResponse response = GenericResponse.builder()
            .message(ex.getMessage() )
            .success(Boolean.FALSE)
            .data(messageSource.getMessage(Constants.MSG_EXCEPTION_INVALID_CREDENTIALS, null, Locale.getDefault() ) )
            .build();

        log.error(response );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /*
     * Metodo que controla la excepcion de que el usuario ya tiene una sesion activa
     * @param UserAlreadyLoggedInException excepcion personalizada cuando el usuario intenta volver a logears cuando ya esta activo
     * @return Response generico con los errores
     */
    @ExceptionHandler(UserAlreadyLoggedInException.class)
    public ResponseEntity<GenericResponse> handleUserAlreadyLogged(UserAlreadyLoggedInException ex) {
        GenericResponse response = GenericResponse.builder()
            .message(ex.getMessage() )
            .success(Boolean.FALSE)
            .data(ex.getMessage() )
            .build();

        log.error(response );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
