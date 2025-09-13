package dev.gerardomarquez.controllers;

import java.util.Optional;

import dev.gerardomarquez.responses.RequestConversationReceivedResponse;
import dev.gerardomarquez.services.ConversationRequestsServiceI;
import dev.gerardomarquez.utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(Constants.MSG_ALERT_TITLE_CONVERSATION_REQUEST_ACEPTED);
        alert.setHeaderText(Constants.MSG_ALERT_HEADER_CONVERSATION_REQUEST_ACEPTED);
        alert.setContentText(Constants.MSG_ALERT_CONTENT_CONVERSATION_REQUEST_ACEPTED);

        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            this.conversationRequestService.putStatusRequestConversationsReceived(
                this.conversationRequest.id(),
                Constants.StatusRequestConversation.ACCEPTED.getValue()
            );
        }
    }

    /*
     * Metodo que se ejecuta al dar click en el boton de rechazar
     */
    @FXML
    public void buttonRejectOnAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(Constants.MSG_ALERT_TITLE_CONVERSATION_REQUEST_REJECTED);
        alert.setHeaderText(Constants.MSG_ALERT_HEADER_CONVERSATION_REQUEST_REJECTED);
        alert.setContentText(Constants.MSG_ALERT_CONTENT_CONVERSATION_REQUEST_REJECTED);

        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            this.conversationRequestService.putStatusRequestConversationsReceived(
                this.conversationRequest.id(),
                Constants.StatusRequestConversation.REJECTED.getValue()
            );
        }
    }

}
