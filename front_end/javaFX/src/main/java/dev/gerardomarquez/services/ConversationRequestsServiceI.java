package dev.gerardomarquez.services;

import dev.gerardomarquez.responses.RequestConversationCreatedResponse;
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
     * Metodo que obtiene todas las peticiones de conversacion en un obserbable
     * @return ObservableSet<RequestConversationCreatedResponse> lista de peticiones de conversaciones que ha mandado
     * el usuario
     */
    public ObservableSet<RequestConversationCreatedResponse> getAllRequestsConversations();

    /*
     * Metodo que elimina una peticion de conversacion a partir de su id
     * @param requestConversation Peticion de conversacion que contiene el id
     */
    public void deleteOneRequesConversation(RequestConversationCreatedResponse requestConversation);

}
