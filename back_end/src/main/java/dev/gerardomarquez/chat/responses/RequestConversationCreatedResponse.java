package dev.gerardomarquez.chat.responses;

import java.time.LocalDateTime;
import java.util.UUID;

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
    LocalDateTime date,
    /*
     * Id de la peticion de conversacion
     */
    UUID id
) {}
