package dev.gerardomarquez.chat.requests;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/*
 * Clase DTO para mandar las colas de las peticiones de conversacion
 */
@Data
@Builder
public class SendQueueConversationRequest implements Serializable {

    /*
     * Usuario quien envia la peticion
     */
    private String fromUserName;

    /*
     * Fecha en la que envio la peticion
     */
    private LocalDateTime date;

    /*
     * Cuerpo del mensaje de la peticion
     */
    private String message;
}
