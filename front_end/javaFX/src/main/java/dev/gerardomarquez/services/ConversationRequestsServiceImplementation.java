package dev.gerardomarquez.services;

import dev.gerardomarquez.requests.RequestConversationPost;
import dev.gerardomarquez.responses.GenericResponse;
import dev.gerardomarquez.rest.RequestConversationApiRest;
import dev.gerardomarquez.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*
* {@inheritDoc}
*/
public class ConversationRequestsServiceImplementation implements ConversationRequestsServiceI {

    /*
     * Objeto que hace las peticiones restfull
     */
    private RequestConversationApiRest requestConversationApiRest;

    /*
     * Constructor por defecto para inicializar las variables
     */
    public ConversationRequestsServiceImplementation(){
        this.requestConversationApiRest = new RequestConversationApiRest();
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public Alert sendRequestConversationToUser(String user) {
        RequestConversationPost request = new RequestConversationPost();
        request.setUserNameTarget(user);
        GenericResponse<String> response = requestConversationApiRest.postRequestConversationSend(request);

        if(response.getSuccess() ){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(Constants.MSG_CONFIRMATION_DIALOG);
            alert.setHeaderText(Constants.MSG_CONFIRMATION_CONVERSATION_REQUEST_HEADER_ALERT);
            alert.setContentText(response.getData() );

            return alert;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.MSG_ERROR_DIALOG);
            alert.setHeaderText(Constants.MSG_ERROR_CONVERSATION_REQUEST_HEADER_ALERT);
            alert.setContentText(response.getData() );

            return alert;
        }
    }

}
