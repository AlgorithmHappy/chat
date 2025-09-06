package dev.gerardomarquez.chat.exceptions;

/*
 * Excepcion personalizada para cuando se supera el limite de peticiones que se
 * puede enviar a un mismo usuario
 */
public class TooManyConversationRequestsException extends RuntimeException {
    
    /*
     * Constructor que ejecuta a el pader, solo seteamos el mensaje de error
     * @para Mensaje de error
     */
    public TooManyConversationRequestsException(String message){
        super(message);
    }
}
