package dev.gerardomarquez.requests;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String nickName;

    /*
     * Contraseña
     */
    private String password;
}
