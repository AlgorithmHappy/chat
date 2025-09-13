package dev.gerardomarquez.chat.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/*
 * Registro que define la peticion para cambiar el estado de una peticion de conversacion
 */
public record ChangeStatusRequestConversationRequest(

    /*
    * Id de la peticion de conversacion
    */
    @NotBlank(message = "El id de la peticion de conversacion no puede estar vacio")
    @NotNull(message = "El id de la peticion de conversacion no puede ser nulo")
    String requestConversationId,
    
    /*
     * Nuevo estado de la peticion de conversacion
     */
    @NotBlank(message = "El estado de la peticion de conversacion no puede estar vacio")
    @NotNull(message = "El estado de la peticion de conversacion no puede ser nulo")
    @Pattern(
        regexp = "pending|rejected|accepted|received",
        message = "El estado debe ser pending, rejected, accepted o received"
    )
    String status
) {

}
