package dev.gerardomarquez.chat.requests;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Clase que se recive por parte del front end para la solicitud de una conversacion
 */
@Data
@NoArgsConstructor
public class InsertRequestConversatinRequest implements Serializable {

    /*
     * Nombre de usuario con el que se quiere comunicar
     */
    @NotBlank(message = "{InsertUserRequest.nickName.NotBlank}")
    @NotNull(message = "{InsertUserRequest.nickName.NotNull}")
    @Size(message = "{InsertUserRequest.nickName.Size}", min = 5, max = 50)
    @Pattern(message = "{InsertUserRequest.nickName.Pattern}", regexp = "^[A-Za-z0-9]+$")
    private String userNameTarget;

}
