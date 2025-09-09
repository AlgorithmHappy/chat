package dev.gerardomarquez.chat.srvices;

import org.springframework.stereotype.Service;

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
     * Metodo que obtiene todas las peticiones de conversaciones de un usuario
     * @param token Contiene el nombre de usuario de quien envia la peticion.
     * @return Response generico
     */
    public GenericResponse getAllRequestConversation(String token);
}
