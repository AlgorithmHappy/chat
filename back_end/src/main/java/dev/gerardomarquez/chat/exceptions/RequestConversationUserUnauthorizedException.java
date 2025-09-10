package dev.gerardomarquez.chat.exceptions;

/*
 * Excepcion que arrojare cuando el usuario que quiera eliminar la peticion de conversacion
 * no sea el que hizo la peticion
 */
public class RequestConversationUserUnauthorizedException extends RuntimeException {

    /*
     * Constructor que ejecuta a el padre, solo seteamos el mensaje de error
     * @param Mensaje de error
     */
    public RequestConversationUserUnauthorizedException(String message){
        super(message);
    }
}
