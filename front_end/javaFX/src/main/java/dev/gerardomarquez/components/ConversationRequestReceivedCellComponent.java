package dev.gerardomarquez.components;

import java.io.IOException;

import dev.gerardomarquez.controllers.LoginItemConversationRequestReceived;
import dev.gerardomarquez.responses.RequestConversationReceivedResponse;
import dev.gerardomarquez.services.ConversationRequestsServiceI;
import dev.gerardomarquez.utils.Constants;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;

/*
 * Clase que crea un componente de la lista del listView de las peticiones de conversacion recibidas
 */
@Log4j2
public class ConversationRequestReceivedCellComponent extends ListCell<RequestConversationReceivedResponse> {

    /*
     * Layout principal del item
     */
    private AnchorPane root;

    /*
     * Servicion que gestiona las peticiones de conversacion
     */
    private ConversationRequestsServiceI conversationRequestService;

    /*
     * Controlador del Componente item de cada celda del list view
     */
    private LoginItemConversationRequestReceived itemController;

    /*
     * Constructor con el servicio
     * @param conversationRequestService Servicio que gestiona las peticiones de conversacion
     */
    public ConversationRequestReceivedCellComponent(ConversationRequestsServiceI conversationRequestService) {
        this.conversationRequestService = conversationRequestService;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_RESOURCES_COMPONENT_CONVERSATION_REQUEST_RECEIVED) );
            root = loader.load();
            VBox wrapper = new VBox(root);
            wrapper.setPadding(new Insets(Constants.ITEM_WRAPPER_PADDING, 0, 5, 0) );
            itemController = loader.getController();
        } catch (IOException e) {
            log.error(e.getMessage() );
        }
    }

    /*
     * Metodo que actualiza el item, que sobreescribe al padre y tambien hace lo del metodo padre
     * @param request Objeto que contiene toda la informacion de la peticion de conversacion
     * @param empty
     */
    @Override
    protected void updateItem(RequestConversationReceivedResponse request, boolean empty) {
        super.updateItem(request, empty);
        if (empty || request == null) {
            setGraphic(null);
        } else {
            itemController.setData(conversationRequestService, request);
            setGraphic(root);
        }
    }
}
