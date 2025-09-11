package dev.gerardomarquez.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.gerardomarquez.requests.RequestConversationPost;
import dev.gerardomarquez.responses.GenericResponse;
import dev.gerardomarquez.responses.RequestConversationCreatedResponse;
import dev.gerardomarquez.responses.RequestConversationReceivedResponse;
import dev.gerardomarquez.rest.RequestConversationApiRest;
import dev.gerardomarquez.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*
* {@inheritDoc}
*/
public class ConversationRequestsServiceImplementation implements ConversationRequestsServiceI {

    /*
     * Coleccion que se actualizara automaticamente cuando se eliminen o hayan nuevas peticiones de conversacion
     * enviadas
     */
    ObservableSet<RequestConversationCreatedResponse> conversationsRequestsSended;

    /*
     * Coleccion que se actualizara automaticamente cuando se eliminen o hayan nuevas peticiones de conversacion
     * recibidas
     */
    ObservableSet<RequestConversationReceivedResponse> conversationsRequestsReceived;


    /*
     * Objeto que hace las peticiones restfull
     */
    private RequestConversationApiRest requestConversationApiRest;

    /*
     * Constructor por defecto para inicializar las variables
     */
    public ConversationRequestsServiceImplementation(){
        this.requestConversationApiRest = new RequestConversationApiRest();
        this.conversationsRequestsSended = FXCollections.observableSet();
        this.conversationsRequestsReceived = FXCollections.observableSet();
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public Alert sendRequestConversationToUser(String user) {
        RequestConversationPost request = new RequestConversationPost();
        request.setUserNameTarget(user);
        GenericResponse<RequestConversationCreatedResponse> response = requestConversationApiRest.postRequestConversationSend(request);

        conversationsRequestsSended.add(response.getData() );

        if(response.getSuccess() ){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(Constants.MSG_CONFIRMATION_DIALOG);
            alert.setHeaderText(Constants.MSG_CONFIRMATION_CONVERSATION_REQUEST_HEADER_ALERT);
            alert.setContentText(Constants.MSG_REQUEST_CONVERSATION_SUCCESS );

            return alert;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.MSG_ERROR_DIALOG);
            alert.setHeaderText(Constants.MSG_ERROR_CONVERSATION_REQUEST_HEADER_ALERT);
            alert.setContentText(response.getMessage() );

            return alert;
        }
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public ObservableSet<RequestConversationCreatedResponse> getAllRequestsConversationsSended() {
        GenericResponse<List<RequestConversationCreatedResponse> > response = requestConversationApiRest.getAll();
        Set<RequestConversationCreatedResponse> setRequestConversation = new HashSet<>(response.getData() );
        this.conversationsRequestsSended.addAll(setRequestConversation);
        return this.conversationsRequestsSended;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public void deleteOneRequesConversation(RequestConversationCreatedResponse requestConversation) {
        requestConversationApiRest.deleteOne(requestConversation.id() );
        this.conversationsRequestsSended.remove(requestConversation);
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public ObservableSet<RequestConversationReceivedResponse> getAllRequestsConversationsReceived() {
        GenericResponse<List<RequestConversationReceivedResponse> > response = requestConversationApiRest.getAllByTarget();
        Set<RequestConversationReceivedResponse> setRequestConversation = new HashSet<>(response.getData() );
        this.conversationsRequestsReceived.addAll(setRequestConversation);
        return this.conversationsRequestsReceived;
    }

}
