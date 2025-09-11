package dev.gerardomarquez.chat.exceptions;

/*
 * Excepcion que arrojare cuando el estatus de la peticion de conversacion que se quiere eliminar
 * no esta en recivida o pendiente, ya que solo se pueden elimiar de estos tipos
 */
public class RequestConversationStatusUnauthorizedException extends RuntimeException {

     /*
     * Constructor que ejecuta a el padre, solo seteamos el mensaje de error
     * @param Mensaje de error
     */
    public RequestConversationStatusUnauthorizedException(String message){
        super(message);
    }
}
