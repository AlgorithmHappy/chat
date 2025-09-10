package dev.gerardomarquez.controllers;

import dev.gerardomarquez.responses.RequestConversationCreatedResponse;
import dev.gerardomarquez.services.ConversationRequestsServiceI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/*
 * Controlador de los items que representan las peticiones enviadas en el listview
 */
public class LoginItemConversationRequestSended {

    /*
     * Boton de eliminacion
     */
    @FXML
    private Button buttonDelete;

    /*
     * Texto de la fecha
     */
    @FXML
    private Label labelDate;

    /*
     * Estatus de la peticion
     */
    @FXML
    private Label labelStatus;

    /*
     * Nombre de usuario a quien se envio la peticion
     */
    @FXML
    private Label labelUserName;

    /*
     * Objeto que contiene los datos del item
     */
    private RequestConversationCreatedResponse conversationRequest;

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
        RequestConversationCreatedResponse conversationRequest
    ) {
        this.conversationRequestService = conversationRequestService;
        this.conversationRequest = conversationRequest;
        this.labelDate.setText(conversationRequest.date() );
        this.labelUserName.setText(conversationRequest.targetUserName() );
        this.labelStatus.setText(conversationRequest.status() );
    }


    /*
     * Se ejecuta cuando se presiona el boton de borrado, este mandara a ejecutar el metodo
     * que borra un item del obserbable set de las peticiones de conversacion
     * 
     */
    @FXML
    public void buttonDeleteOnAction() {
        /*
         * Aqui deberia de ejecutar el servicio para eliminar el archivo
         */
        //this.conversationRequestService.delete(this.conversationRequest.id() );
    }

}

