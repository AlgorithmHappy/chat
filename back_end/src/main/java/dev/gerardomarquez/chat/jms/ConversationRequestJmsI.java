package dev.gerardomarquez.chat.jms;

import dev.gerardomarquez.chat.requests.DeleteConversationToTarget;
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
    public void sendRequestConversationQueueToUsuer(SendQueueConversationRequest requestQueue, String usernameId);

    /*
     * Metodo que borra la peticion de conversacion del lado del "target"
     * @param requestConversationId Objeto con el id de la peticion de conversacion a eliminar
     * @param targetId Id del usuario a quien se le va a eliminar la peticion, es la propiedad de la cola
     * para que pueda llegar a su destinatario
     */
    public void sendRequestConversationQueueToUsuer(DeleteConversationToTarget requestConversationId, String targetId);
}
