package dev.gerardomarquez.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Clase dto para enviar la peticion a requestConversation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestConversationPost {
    
    /*
     * Nombre de usuario a enviar la solicitud de conversacion
     */
    private String userNameTarget;
}
