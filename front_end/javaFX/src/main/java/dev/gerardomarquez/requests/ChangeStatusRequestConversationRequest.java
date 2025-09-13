package dev.gerardomarquez.requests;

/*
 * Registro que define la peticion para cambiar el estado de una peticion de conversacion
 */
public record ChangeStatusRequestConversationRequest(

    /*
    * Id de la peticion de conversacion
    */
    String requestConversationId,
    
    /*
     * Nuevo estado de la peticion de conversacion
     */
    String status
) {

}
