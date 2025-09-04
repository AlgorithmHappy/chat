package dev.gerardomarquez.chat.utils;

/*
 * Clase que contiene las constantes
 */
public class Constants {
    public static final String MSG_SUCCESS = "request.success";
    public static final String MSG_DB_CONTRAINT_UNIQUE_VIOLATED = "constaint.unique.violated";
    public static final String MSG_RATE_LIMIT = "rate.limit";
    public static final String MSG_EXCEPTION_RATE_LIMIT = "No es posible realizar otra peticion porque excedio el limite de peticions";

    public static final String RATE_LIMIT_PATH_ONE = "/users/singUp";

    public static final Integer HTTP_STATUS_CODE_RATE_LIMIT = 429;

}
