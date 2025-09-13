package dev.gerardomarquez.chat.srvices;

import org.springframework.stereotype.Service;

import dev.gerardomarquez.chat.requests.ChangeStatusRequestConversationRequest;
import dev.gerardomarquez.chat.requests.InsertRequestConversatinRequest;
import dev.gerardomarquez.chat.responses.GenericResponse;

/*
 * Interfaz que define los metodos para gestionar los usuarios
 */
@Service
public interface ConversationRequestServiceI {

    /*
     * Metodo que inserta en base de datos y envia la cola a activemq de la peticion de conversacion
     * @param request Contiene el nombre de usuario al que le va a llegar la peticion.
     * @param token Contiene el nombre de usuario de quien envia la peticion.
     * @return Response generico
     */
    public GenericResponse insertOneRequestConversation(InsertRequestConversatinRequest requst, String token);

    /*
     * Metodo que obtiene todas las peticiones de conversaciones de un usuario "requester"
     * @param token Contiene el nombre de usuario de quien envia la peticion.
     * @return Response generico
     */
    public GenericResponse getAllRequestConversationByRequester(String token);

    /*
     * Metodo que elimina una peticion de conversacion
     * @param token Contiene el nombre de usuario de quien envia la peticion.
     * @param requestConversationId Contiene el id en tipo string.
     */
    public void deleteOneRequestConversation(String token, String requestConversationId);

    /*
     * Metodo que obtiene todas las peticiones de conversaciones de un usuario "target"
     * @param token Contiene el nombre de usuario de quien se recive la peticion.
     * @return Response generico
     */
    public GenericResponse getAllRequestConversationByTarget(String token);

    /*
     * Metodo que cambia el estado de una peticion de conversacion
     * @param token Contiene el nombre de usuario de quien se recive la peticion.
     * @param request Contiene el id de la peticion y el nuevo estado
     * @return Response generico
     */
    public void putOneRequestConversationById(String token, ChangeStatusRequestConversationRequest request);
}
