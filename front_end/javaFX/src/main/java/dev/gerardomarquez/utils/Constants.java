package dev.gerardomarquez.utils;

/*
 * Clase que contiene las constantes
 */
public class Constants {

    /*
     * Rutas de archivos
     */
    public static final String PATH_RESOURCES_PROPERTIES = "/config.properties";
    public static final String PATH_RESOURCES_VIEW_LOGIN = "/dev/gerardomarquez/Login.fxml";
    public static final String PATH_RESOURCES_VIEW_CHAT = "/dev/gerardomarquez/Chat.fxml";
    public static final String PATH_RESOURCES_COMPONENT_CONVERSATION_REQUEST_SENDED = "/dev/gerardomarquez/LoginConversationRequestSended.fxml";

    /*
     * Errores
     */
    public static final String ERROR_PROPERTIES_NOT_FOUND = "No se encontró el archivo config.properties";
    public static final String ERROR_PROPERTIES_NOT_UPLOAD = "Error cargando config.properties";

    /*
     * Claves de las propiedades
     */
    public static final String PROPIERTIES_REST_URL = "rest.url";
    public static final String PROPIERTIES_REST_PATH_USERS_SIGNUP = "rest.path.users.signUp";
    public static final String PROPIERTIES_REST_PATH_USERS_SIGNIN = "rest.path.users.signIn";
    public static final String PROPIERTIES_REST_PATH_USERS_LOGOUT = "rest.path.users.logOut";
    public static final String PROPIERTIES_REST_PATH_CONVERSATION_REQUEST_SEND = "rest.path.conversation.request.send";
    public static final String PROPIERTIES_REST_PATH_CONVERSATION_REQUEST_GET_ALL = "rest.path.conversation.request.getAll";
    public static final String PROPIERTIES_REST_PATH_CONVERSATION_REQUEST_DELETE_ONE = "rest.path.conversation.request.deleteOne";

    /*
     * Mensajes
     */
    public static final String MSG_CONFIRMATION_DIALOG = "Confirmación";
    public static final String MSG_NEW_USER_INSERTED_HEADER_DIALOG = "Nuevo usuario creado.";
    public static final String MSG_NEW_USER_INSERTED_TEXT_DIALOG = "¡Usuario %s creado con éxito!";
    public static final String MSG_ERROR_DIALOG = "Error";
    public static final String MSG_ERROR_NEW_USER_INSERTED_HEADER_DIALOG = "Se produjeron uno o varios errores.";
    public static final String MSG_ERROR_NEW_USER_UNIQUE_CONSTRAINT_DIALOG = "El nombre de usuario ingresado ya esta registrado";
    public static final String MSG_ERROR_GENERAL_HEADER_DIALOG = "Se produjo un error inesperado";
    public static final String MSG_ERROR_GENERAL_TEXT_DIALOG = "Oops, algo salió mal. Contacta con el administrador para más información.";
    public static final String MSG_ERROR_NET_TEXT_DIALOG = "Oops, no se pudo conectar: revisa tu internet o intenta más tarde.";
    public static final String MSG_ERROR_LOG_ALERT_DIALOG_IS_NOT_PRESENT = "El objeto 'Alert' no esta presnete en el optional.";
    public static final String MSG_ERROR_DIALOG_USERNAME_BLANK = "No se ha ingresado el nombre de usuario.";
    public static final String MSG_ERROR_DIALOG_TITLE = "Error en el dato de entrada.";
    public static final String MSG_DIALOG_TITLE_CONVERSATION_REQUEST = "Envío de solicitud";
    public static final String MSG_DIALOG_TEXT_CONVERSATION_REQUEST = "Ingrese el nombre de usuario para enviar la solicitud:";
    public static final String MSG_CONFIRMATION_CONVERSATION_REQUEST_HEADER_ALERT = "Envío de solicitud de conversación.";
    public static final String MSG_ERROR_CONVERSATION_REQUEST_HEADER_ALERT = "Error en el envío de solicitud de conversación.";
    public static final String MSG_ERROR_EMPTY_OPTIONAL = "Optional vacio";
    public static final String MSG_ERROR_GENERAL = "Oops, se produjo un error inesperado";
    public static final String MSG_REQUEST_CONVERSATION_SUCCESS = "¡Listo! Petición enviada con éxito.";
    public static final String MSG_ALERT_TITLE_CONVERSATION_REQUEST_DELETE = "Eliminar solicitud de conversación";
    public static final String MSG_ALERT_HEADER_CONVERSATION_REQUEST_DELETE = "Atención: Estás a punto de eliminar una solicitud.";
    public static final String MSG_ALERT_CONTENT_CONVERSATION_REQUEST_DELETE = "¿Estás seguro de que deseas eliminar esta solicitud? Se borrará para ambas partes de la conversación.";

    /*
     * Titulos de ventanas
     */
    public static final String TITLE_LOGIN = "Ingresar / Registrarse";
    public static final String TITLE_CHAT = "Mensajeria creado por Gerardo Marquez";

    public static final Integer HTTP_STATUS_CODE_UNPROCESSABLE_ENTITY = 422;

    public static final String BEARER = "Bearer ";

    public static final Double VIEW_CHAT_SPLIT_POSITION = 0.273;
}
