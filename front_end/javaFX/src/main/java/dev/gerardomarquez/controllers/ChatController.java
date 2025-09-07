package dev.gerardomarquez.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/*
 * Controlador de la vista principal del chat
 */
public class ChatController {

    /*
     * Boton de agregar amigo
     */
    @FXML
    private Button buttonConversationRequest;

    /*
     * Boton de mandar mensaje
     */
    @FXML
    private Button buttonSendMessage;

    @FXML
    private ListView<?> listViewConversations;

    @FXML
    private ListView<?> listViewMessages;

    @FXML
    private ListView<?> listViewRequestConversationRecived;

    @FXML
    private ListView<?> listViewRequestConversationSended;

    @FXML
    private TextArea textAreaMessage;

    /*
     * Metodo que se ejecuta al presionar el boton de mandar mensaje
     */
    @FXML
    private void buttonSendMessageOnAction(){

    }

    /*
     * Metodo que se ejecuta al presionar el boton de mandar agregar un amigo
     */
    @FXML
    private void buttonConversationRequestOnAction(){

    }
}
