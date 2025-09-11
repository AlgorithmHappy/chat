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

    /*
     * Metodo que controla la excepcion cuando el receptor de la peticion de conversacion no fue encontrado
     * @param IllegalArgumentException excepcion cuando el usuario que recive la peticion de conversacion no fue encontrada
     * @return Response generico con los errores
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericResponse> handleIllegalArgument(IllegalArgumentException ex) {
        GenericResponse response = GenericResponse.builder()
            .message(ex.getMessage() )
            .success(Boolean.FALSE)
            .data(ex.getMessage() )
            .build();

        log.error(response );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /*
     * Metodo que controla la excepcion cuando el receptor de la peticion de conversacion no fue encontrado
     * @param IllegalArgumentException excepcion cuando el usuario que recive la peticion de conversacion no fue encontrada
     * @return Response generico con los errores
     */
    @ExceptionHandler(TooManyConversationRequestsException.class)
    public ResponseEntity<GenericResponse> handleTooManyConversationRequests(TooManyConversationRequestsException ex) {
        GenericResponse response = GenericResponse.builder()
            .message(ex.getMessage() )
            .success(Boolean.FALSE)
            .data(ex.getMessage() )
            .build();

        log.error(response );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    /*
     * Metodo que controla la excepcion cuando se quieren enviar una solicitud de conversacion asi mismos
     * @param SameUserToRequestException excepcion cuando el usuario se quiere mandar una solicitud asi mismo
     * @return Response generico con los errores
     */
    @ExceptionHandler(SameUserToRequestException.class)
    public ResponseEntity<GenericResponse> handleSameUserToRequest(SameUserToRequestException ex) {
        GenericResponse response = GenericResponse.builder()
            .message(ex.getMessage() )
            .success(Boolean.FALSE)
            .data(ex.getMessage() )
            .build();

        log.error(response );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    /*
     * Metodo que controla la excepcion cuando se quieren borrar una solicitud de conversacion de un id que no
     * existe
     * @param RequestConversationNotFoundException excepcion que se lanza cuando la peticion de conversacion no
     * fue encontrada por su id
     * @return Response generico con los errores
     */
    @ExceptionHandler(RequestConversationNotFoundException.class)
    public ResponseEntity<GenericResponse> handleRequestConversationNotFound(RequestConversationNotFoundException ex) {
        GenericResponse response = GenericResponse.builder()
            .message(ex.getMessage() )
            .success(Boolean.FALSE)
            .data(ex.getMessage() )
            .build();

        log.error(response );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /*
     * Metodo que controla la excepcion cuando se quiere borrar una solicitud de conversacion de un usuario quien no creo
     * dicha solicitud
     * @param RequestConversationUserUnauthorizedException excepcion lanzada cuando un usuario quiere borrar una peticion
     * de conversacion que no fue cread por el 
     * @return Response generico con los errores
     */
    @ExceptionHandler(RequestConversationUserUnauthorizedException.class)
    public ResponseEntity<GenericResponse> handleRequestConversationUserUnauthorized(RequestConversationUserUnauthorizedException ex) {
        GenericResponse response = GenericResponse.builder()
            .message(ex.getMessage() )
            .success(Boolean.FALSE)
            .data(ex.getMessage() )
            .build();

        log.error(response );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /*
     * Metodo que controla la excepcion cuando se quiere borrar una solicitud de conversacion de un estatud no permitido
     * @param RequestConversationStatusUnauthorizedException excepcion lanzada cuando un usuario quiere borrar una peticion
     * de conversacion de un estatus no permitido (aceptada o rechazada)
     * @return Response generico con los errores
     */
    @ExceptionHandler(RequestConversationStatusUnauthorizedException.class)
    public ResponseEntity<GenericResponse> handleRequestConversationStatusUnauthorized(RequestConversationStatusUnauthorizedException ex) {
        GenericResponse response = GenericResponse.builder()
            .message(ex.getMessage() )
            .success(Boolean.FALSE)
            .data(ex.getMessage() )
            .build();

        log.error(response );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
