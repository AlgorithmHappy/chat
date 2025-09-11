package dev.gerardomarquez.controllers;

import java.util.Optional;

import dev.gerardomarquez.components.ConversationRequestCellComponent;
import dev.gerardomarquez.components.ConversationRequestReceivedCellComponent;
import dev.gerardomarquez.responses.RequestConversationCreatedResponse;
import dev.gerardomarquez.responses.RequestConversationReceivedResponse;
import dev.gerardomarquez.services.ConversationRequestsServiceI;
import dev.gerardomarquez.services.ConversationRequestsServiceImplementation;
import dev.gerardomarquez.utils.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

/*
 * Controlador de la vista principal del chat
 */
public class ChatController {

    /*
     * Ventada dividida
     */
    @FXML
    private SplitPane splitPane;

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

    /*
     * ListView de las conversaciones
     */
    @FXML
    private ListView<?> listViewConversations;

    /*
     * ListView del chat
     */
    @FXML
    private ListView<?> listViewMessages;

    /*
     * ListView de las peticiones de conversaciones recividas
     */
    @FXML
    private ListView<?> listViewRequestConversationRecived;

    /*
     * ListView de las peticiones de conversacion enviadas
     */
    @FXML
    private ListView<RequestConversationCreatedResponse> listViewRequestConversationSended;

    /*
     * ListView de las peticiones de conversacion recibidas
     */
    @FXML
    private ListView<RequestConversationReceivedResponse> listViewRequestConversationReceived;

    /*
     * Input text donde el usuario puede escribir su mensaje
     */
    @FXML
    private TextArea textAreaMessage;

    /*
     * Clase de servicio para enviar la peticion de conversacion
     */
    private ConversationRequestsServiceI conversationRequestsService;

    @FXML
    private void initialize() {
        this.conversationRequestsService = new ConversationRequestsServiceImplementation();
        splitPane.setDividerPositions(Constants.VIEW_CHAT_SPLIT_POSITION);
        SplitPane.Divider divider = splitPane.getDividers().get(0);
        divider.positionProperty().addListener((obs, oldVal, newVal) -> {
            divider.setPosition(Constants.VIEW_CHAT_SPLIT_POSITION);
        });

        ObservableSet<RequestConversationCreatedResponse> setConversationSended = conversationRequestsService
            .getAllRequestsConversationsSended();
        ObservableList<RequestConversationCreatedResponse> listConversationSended = FXCollections
            .observableArrayList(setConversationSended);
        listViewRequestConversationSended.setItems(listConversationSended);

        setConversationSended.addListener(
            (SetChangeListener<RequestConversationCreatedResponse>) change -> {
                Platform.runLater(
                    () -> {
                        if (change.wasAdded() ) listConversationSended.add(change.getElementAdded() );
                        if (change.wasRemoved() ) listConversationSended.remove(change.getElementRemoved() );
                    }
                );
            }
        );

        listViewRequestConversationSended.setCellFactory(
            lv -> new ConversationRequestCellComponent(conversationRequestsService)
        );


        ObservableSet<RequestConversationReceivedResponse> setConversationReceived = conversationRequestsService
            .getAllRequestsConversationsReceived();
        ObservableList<RequestConversationReceivedResponse> listConversationReceived = FXCollections
            .observableArrayList(setConversationReceived);
        listViewRequestConversationReceived.setItems(listConversationReceived);

        setConversationReceived.addListener(
            (SetChangeListener<RequestConversationReceivedResponse>) change -> {
                Platform.runLater(
                    () -> {
                        if (change.wasAdded()) listConversationReceived.add(change.getElementAdded() );
                        if (change.wasRemoved()) listConversationReceived.remove(change.getElementRemoved() );
                    }
                );
            }
        );

        listViewRequestConversationReceived.setCellFactory(
            lv -> new ConversationRequestReceivedCellComponent(conversationRequestsService)
        );
    }

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

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(Constants.MSG_DIALOG_TITLE_CONVERSATION_REQUEST);
        dialog.setHeaderText(Constants.MSG_DIALOG_TEXT_CONVERSATION_REQUEST);

        Optional<String> optionalUserName = dialog.showAndWait();

        if(optionalUserName.isEmpty() ){
            Alert alert = new Alert(AlertType.ERROR );
            alert.setTitle(Constants.MSG_ERROR_DIALOG);
            alert.setHeaderText(Constants.MSG_ERROR_DIALOG_TITLE);
            alert.setContentText(Constants.MSG_ERROR_DIALOG_USERNAME_BLANK);
            alert.show();
            return;
        } 
        
        if(optionalUserName.get().isBlank() ){
            Alert alert = new Alert(AlertType.ERROR );
            alert.setTitle(Constants.MSG_ERROR_DIALOG);
            alert.setHeaderText(Constants.MSG_ERROR_DIALOG_TITLE);
            alert.setContentText(Constants.MSG_ERROR_DIALOG_USERNAME_BLANK);
            alert.show();
            return;
        }
        
        Alert alert = conversationRequestsService.sendRequestConversationToUser(optionalUserName.get() );
        alert.show();

    }
}
