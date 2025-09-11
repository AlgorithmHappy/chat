package dev.gerardomarquez.controllers;

import dev.gerardomarquez.responses.RequestConversationReceivedResponse;
import dev.gerardomarquez.services.ConversationRequestsServiceI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/*
 * Controlador de los items que representan las peticiones recibidas en el listview
 */
public class LoginItemConversationRequestReceived {

    /*
     * Boton de aceptacion
     */
    @FXML
    private Button buttonAcept;

    /*
     * Boton de rechazo
     */
    @FXML
    private Button buttonReject;

    /*
     * Texto de la fecha
     */
    @FXML
    private Label labelDate;

    /*
     * Nombre de usuario que envio la peticion
     */
    @FXML
    private Label labelUserName;

    /*
     * Objeto que contiene los datos del item
     */
    private RequestConversationReceivedResponse conversationRequest;

    /*
     * Servicio que hace la gestion de las peticiones de conversaciones
     */
    private ConversationRequestsServiceI conversationRequestService;

    /*
     * Metodo que inicializa todos los objetos que se van a ocupar
     * @param conversationRequestService Servicio que gestiona las peticiones de conversacion
     * @param conversationRequest Objeto que contiene los datos del item (de la peticion de conversacion)
     */
    public void setData(
        ConversationRequestsServiceI conversationRequestService,
        RequestConversationReceivedResponse conversationRequest
    ) {
        this.conversationRequestService = conversationRequestService;
        this.conversationRequest = conversationRequest;
        this.labelDate.setText(conversationRequest.date() );
        this.labelUserName.setText(conversationRequest.requesterUserName() );
    }

    /*
     * Metodo que se ejecuta al dar click en el boton de aceptar
     */
    @FXML
    public void buttonAceptOnAction() {

    }

    /*
     * Metodo que se ejecuta al dar click en el boton de rechazar
     */
    @FXML
    public void buttonRejectOnAction() {

    }

}
