package dev.gerardomarquez.services;

import dev.gerardomarquez.responses.RequestConversationCreatedResponse;
import dev.gerardomarquez.responses.RequestConversationReceivedResponse;
import javafx.collections.ObservableSet;
import javafx.scene.control.Alert;

/*
 * Interfaz que define los metodos para gestionar las peticiones de conversacion
 */
public interface ConversationRequestsServiceI {

    /*
     * Metodo que envia la peticion de conversacion
     * @param Nombre de usuario
     * @return Alert que mostrara el exito o error
     */
    public Alert sendRequestConversationToUser(String user);

    /*
     * Metodo que obtiene todas las peticiones de conversaciones enviadas en un obserbable
     * @return ObservableSet<RequestConversationCreatedResponse> lista de peticiones de conversaciones que ha mandado
     * el usuario
     */
    public ObservableSet<RequestConversationCreatedResponse> getAllRequestsConversationsSended();

    /*
     * Metodo que elimina una peticion de conversacion a partir de su id
     * @param requestConversation Peticion de conversacion que contiene el id
     */
    public void deleteOneRequesConversation(RequestConversationCreatedResponse requestConversation);

    /*
     * Metodo que obtiene todas las peticiones de conversacion recividas en un obserbable
     * @return ObservableSet<RequestConversationReceivedResponse> lista de peticiones de conversaciones que ha recivido
     * el usuario
     */
    public ObservableSet<RequestConversationReceivedResponse> getAllRequestsConversationsReceived();

    /*
     * Metodo que cambia el estado de una peticion de conversacion
     * @param id Id de la peticion de conversacion
     * @param status Nuevo estado de la peticion de conversacion
     * status puede ser: pending, rejected, accepted o received
     */
    public void putStatusRequestConversationsReceived(
        String id,
        String status
    );

}
