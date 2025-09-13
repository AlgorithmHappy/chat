package dev.gerardomarquez.chat.responses;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * Record que se devolvera en el caso de que se obtengan las peticiones de conversacion
 * de un usuario "target"
 */
public record RequestConversationRecivedResponse(
    /*
     * Nombre de usuario de quien llega la peticion
     */
    String requesterUserName,

    /*
     * Estado de la peticion (PENDIENTE, ACEPTADA, RECHAZADA, CANCELADA)
     */
    String status,

    /*
     * Fecha en formato dd/mm/yyyy HH:mm:ss
     */
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime date,
    
    /*
     * Id de la peticion de conversacion
     */
    UUID id
) {
}
