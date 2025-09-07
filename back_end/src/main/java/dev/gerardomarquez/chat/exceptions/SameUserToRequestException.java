package dev.gerardomarquez.chat.exceptions;

/*
 * Excepcion cuando el usuario quiere mandarse a si mismo una solicitud de conversacion
 */
public class SameUserToRequestException extends RuntimeException {

    /*
     * Constructor que ejecuta a el padre, solo seteamos el mensaje de error
     * @param Mensaje de error
     */
    public SameUserToRequestException(String message){
        super(message);
    }
}
