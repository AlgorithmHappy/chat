package dev.gerardomarquez.chat.exceptions;

/*
 * Excepcion personalizada para el rate limit
 */
public class RateLimitExceededException extends RuntimeException {

    /*
     * Constructor que llama al constructor de RuntimeException y que sobreesxribe el mensaje
     */
    public RateLimitExceededException(String message) {
        super(message);
    }
}
