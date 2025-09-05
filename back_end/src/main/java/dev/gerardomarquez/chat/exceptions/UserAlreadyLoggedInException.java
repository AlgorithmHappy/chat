package dev.gerardomarquez.chat.exceptions;

/*
 * Excepcion personalizada para indicar que el usuario ya tiene una sesion iniciada
 */
public class UserAlreadyLoggedInException extends RuntimeException {

    /*
     * Constructor con el mensaje
     */
    public UserAlreadyLoggedInException(String message) {
        super(message);
    }
}