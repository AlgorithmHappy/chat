package dev.gerardomarquez.services;

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
}
