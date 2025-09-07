package dev.gerardomarquez.chat.utils;

/*
 * Clase que contiene las constantes
 */
public class Constants {
    public static final String MSG_SUCCESS = "request.success";
    public static final String MSG_DB_CONTRAINT_UNIQUE_VIOLATED = "constaint.unique.violated";
    public static final String MSG_RATE_LIMIT = "rate.limit";
    public static final String MSG_EXCEPTION_RATE_LIMIT = "No es posible realizar otra peticion porque excedio el limite de peticions";
    public static final String MSG_EXCEPTION_USERNAME_NOT_FOUND = "InsertUserRequest.nickName.NotFound";
    public static final String MSG_EXCEPTION_INVALID_CREDENTIALS = "InsertUserRequest.invalid.credetials";
    public static final String MSG_EXCEPTION_USER_ALREADY_LOGGED = "exception.user.already.logged";
    public static final String MSG_EXCEPTION_REQUEST_CONVERSATION_NAME_NOT_FOUND = "exception.request.conversation.username.notFound";
    public static final String MSG_EXCEPTION_REQUEST_CONVERSATION_PENDING_FOUND = "exception.request.conversation.pending.found";
    public static final String MSG_EXCEPTION_REQUEST_CONVERSATION_LIMIT_REJECTED = "exception.request.conversation.limit.rejected";
    public static final String MSG_REQUEST_CONVERSATION_MESSAGE = "request.conversation.message";
    public static final String MSG_REQUEST_CONVERSATION_RESPONSE_SUCCESS = "request.conversation.response.success";
    public static final String MSG_REQUEST_CONVERSATION_SAME_USER = "request.conversation.same.user";

    public static final String RATE_LIMIT_PATH_ONE = "/users/signUp";
    public static final String RATE_LIMIT_PATH_TWO = "/users/signIn";
    public static final String UNSECURITY_PATH_ONE = "users/signUp";
    public static final String UNSECURITY_PATH_TWO = "users/signIn";

    public static final Integer HTTP_STATUS_CODE_RATE_LIMIT = 429;

    public static final Long TIME_EXPIRATED_CONVERTER = 3600000L;

    public static final String BEARER = "Bearer ";

    public static final String AUTHORIZATION = "Authorization";

    /*
     * Constantes del estatus de peticiones conversaciones
     */
    public static final String CONVERSATION_REQUEST_STATUS_ONE = "pending";
    public static final String CONVERSATION_REQUEST_STATUS_TWO = "accepted";
    public static final String CONVERSATION_REQUEST_STATUS_THREE = "rejected";

    // Limite de rechazos para no volver a recivir solucitudes de conversacion
    public static final Long CONVERSATION_REQUEST_LIMIT = 3L;

    /*
     * JMS
     */
    public static final String JMS_PROPERTI_KEY_ID_USER = "idUser";
}
