package dev.gerardomarquez.chat.jms;

import dev.gerardomarquez.chat.requests.SendQueueConversationRequest;

/*
 * Interfaz que define los metodos que se encargaran de encolar las peticiones de conversacion
 */
public interface ConversationRequestJmsI {

    /*
     * Metodo que envia la peticion de conversacion a la cola que despues la tomara el usuario que recive
     * @param requestQueue Objeto con los datos de la peticion de conversacion
     * @param usernameId Id del usuario quien va a recivir la peticion, es la propiedad de la cola
     * para que pueda llegar a su destinatario
     */
    public void sendQueueToUsuer(SendQueueConversationRequest requestQueue, String usernameId);
}
