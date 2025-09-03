package dev.gerardomarquez.responses;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Response que se devolvera al registrarse el usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreatedResponse implements Serializable {

    /*
     * Nombre de usuario
     */
    private String nickName;

    /*
     * Fecha de creacion
     */
    private LocalDateTime createdAt;
}
