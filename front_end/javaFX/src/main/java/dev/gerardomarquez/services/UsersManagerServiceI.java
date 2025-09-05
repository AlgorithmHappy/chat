package dev.gerardomarquez.services;

import dev.gerardomarquez.dtos.PassLoginViewDto;
import javafx.scene.control.Alert;

/*
 * Interfaz que define el consumo del servicio para registrar al usuario
 */
public interface UsersManagerServiceI {

    /*
     * Metodo que consume el serivicio para insertar un nuevo usuario.
     * @param user Nombre de usuario.
     * @param password Contraseña para despues ingresar.
     * @return Devuelve una alerta para indicar si se registro correctamente el usuario.
     */
    public Alert insertOneUser(String user, String password);

    /*
     * Metodo que consume el serivicio para hacer login o ingresar.
     * @param user Nombre de usuario.
     * @param password Contraseña para despues ingresar.
     * @return Devuelve un dto para cambiar de escena o mandar una alerta en el caso de que haya error.
     */
    public PassLoginViewDto login(String user, String password);

    /*
     * Metodo cierra sesion
     * @param token Token generado por el login
     */
    public void logout(String token);
}
