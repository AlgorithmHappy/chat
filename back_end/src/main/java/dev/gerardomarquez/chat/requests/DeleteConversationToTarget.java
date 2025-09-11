package dev.gerardomarquez.chat.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Clase que representa la peticion para eliminar una conversacion del lado del target
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteConversationToTarget {

    /*
     * Id de la peticion de conversacion
     */
    private String requestConversationId;
}
