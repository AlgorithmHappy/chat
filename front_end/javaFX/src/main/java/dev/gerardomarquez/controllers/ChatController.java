package dev.gerardomarquez.controllers;

import java.util.Optional;

import dev.gerardomarquez.services.ConversationRequestsServiceI;
import dev.gerardomarquez.services.ConversationRequestsServiceImplementation;
import dev.gerardomarquez.utils.Constants;
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
     * Clase de servicio para enviar la peticion de conversacion
     */
    private ConversationRequestsServiceI conversationRequestsService;

    @FXML
    private void initialize() {
        this.conversationRequestsService = new ConversationRequestsServiceImplementation();

        // Fijamos divisor en 30%
        splitPane.setDividerPositions(0.25);

        // Obtenemos el primer divisor (en este caso solo hay uno)
        SplitPane.Divider divider = splitPane.getDividers().get(0);

        // Evitamos que el usuario lo mueva
        divider.positionProperty().addListener((obs, oldVal, newVal) -> {
            divider.setPosition(0.3);
        });

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
