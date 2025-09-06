package dev.gerardomarquez.chat.requests;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/*
 * Clase que se recive por parte del front end para la solicitud de una conversacion
 */
@Builder
@Data
public class InsertRequestConversatinRequest implements Serializable {

    /*
     * Nombre de usuario con el que se quiere comunicar
     */
    private String userNameTarget;

}
