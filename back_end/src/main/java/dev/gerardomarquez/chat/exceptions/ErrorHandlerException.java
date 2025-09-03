package dev.gerardomarquez.chat.exceptions;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            .map(
                it -> {
                    return messageSource.getMessage(
                        Constants.MSG_ERROR_BAD_REQUEST,
                        new Object[]{it.getField(), it.getDefaultMessage() },
                        Locale.getDefault()
                    );
                }
            )
            .collect(Collectors.toList() );

        GenericResponse genericResponse = GenericResponse.builder()
            .success(Boolean.FALSE)
            .message(ex.getMessage() )
            .data(listErrors)
            .build();
            
        log.error(ex.getMessage() );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(genericResponse);
    }
}
