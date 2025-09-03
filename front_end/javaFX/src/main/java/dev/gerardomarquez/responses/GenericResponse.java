package dev.gerardomarquez.responses;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Clase generica que devolvera todos los endpoints
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponse<T> implements Serializable {

    /*
     * Indica si fue exitoso o no la operacion
     */
    private Boolean success;

    /*
     * Mensaje de error o exito
     */
    private String message;

    /*
     * Datos del response
     */
    private T data;
}
