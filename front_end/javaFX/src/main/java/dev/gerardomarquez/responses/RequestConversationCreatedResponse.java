package dev.gerardomarquez.responses;

/*
 * Record que debera de llenarse en el caso de que el post de la peticion de conversacion
 * haya sido exitosa
 */
public record RequestConversationCreatedResponse(
    /*
     * Nombre de usuario a quien se mando la peticion
     */
    String targetUserName,
    /*
     * Estatus actual
     */
    String status,
    /*
     * Fecha en formato dd/mm/yyyy HH:mm:ss
     */
    String date,
    /*
     * Id de la peticion de conversacion
     */
    String id
) {}
