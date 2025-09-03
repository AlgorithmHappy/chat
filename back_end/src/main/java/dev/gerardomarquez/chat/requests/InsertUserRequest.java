package dev.gerardomarquez.chat.requests;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

/*
 * Clase que envia el front end en el request para crear un nuevo usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsertUserRequest implements Serializable {
    /*
     * Nombre de usuario
     */
    @NotBlank(message = "{InsertUserRequest.nickName.NotBlank}")
    @NotNull(message = "{InsertUserRequest.nickName.NotNull}")
    @Size(message = "{InsertUserRequest.nickName.Size}", min = 5, max = 50)
    @Pattern(message = "{InsertUserRequest.nickName.Pattern}", regexp = "^[A-Za-z0-9]+$")
    private String nickName;

    /*
     * Contraseña
     */
    @NotNull(message = "{InsertUserRequest.password.NotNull}")
    @Size(message = "{InsertUserRequest.password.Size}", min = 8, max = 12)
    @Pattern(message = "{InsertUserRequest.password.Pattern}", regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]+$")
    private String password;
}
