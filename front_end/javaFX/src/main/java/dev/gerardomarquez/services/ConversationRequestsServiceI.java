package dev.gerardomarquez.services;

import java.util.List;

import dev.gerardomarquez.responses.RequestConversationCreatedResponse;
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
     * Metodo que obtiene todas las peticiones de conversacion
     * @return List<RequestConversationCreatedResponse> lista de peticiones de conversaciones que ha mandado
     * el usuario
     */
    public List<RequestConversationCreatedResponse> getAllRequestsConversations();
}
