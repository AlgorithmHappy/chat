package dev.gerardomarquez.services;

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
}
