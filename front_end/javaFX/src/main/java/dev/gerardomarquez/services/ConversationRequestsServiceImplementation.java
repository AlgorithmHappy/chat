package dev.gerardomarquez.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dev.gerardomarquez.requests.ChangeStatusRequestConversationRequest;
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

        if(!response.getData().id().isEmpty() ){
            conversationsRequestsSended.add(response.getData() );
        }

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
        for(RequestConversationReceivedResponse req : setRequestConversation){
            if(req.status().equals(Constants.StatusRequestConversation.PENDING.getValue() ) ){
                this.putStatusRequestConversationsReceived(req.id(), Constants.StatusRequestConversation.RECEIVED.getValue() );
            }
        }
        return this.conversationsRequestsReceived;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public void putStatusRequestConversationsReceived(String id, String status) {
        ChangeStatusRequestConversationRequest changeStatusRequestConversation = new ChangeStatusRequestConversationRequest(
            id,
            status
        );
        requestConversationApiRest.putOneStatus(changeStatusRequestConversation);

        this.conversationsRequestsReceived.forEach(
            req -> {
                if(req.id().equals(id) ){
                    this.conversationsRequestsReceived.remove(req);
                    if(status.equals(Constants.StatusRequestConversation.REJECTED.getValue() ) ) return;
                    if(status.equals(Constants.StatusRequestConversation.ACCEPTED.getValue() ) ) return;
                    RequestConversationReceivedResponse updatedReq = new RequestConversationReceivedResponse(
                        req.requesterUserName(),
                        status,
                        req.date(),
                        req.id()
                    );
                    this.conversationsRequestsReceived.add(updatedReq);
                }
            }
        );
    }

}
