package dev.gerardomarquez.chat.exceptions;

/*
 * Excepcion que arrojare cuando no se encuentre la peticion de conversacion a buscar
 */
public class RequestConversationNotFoundException extends RuntimeException {
    
    /*
     * Constructor que ejecuta a el padre, solo seteamos el mensaje de error
     * @param Mensaje de error
     */
    public RequestConversationNotFoundException(String message){
        super(message);
    }
}
