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

    /*
     * Mensajes
     */
    public static final String MSG_CONFIRMATION_DIALOG = "Confirmación";
    public static final String MSG_NEW_USER_INSERTED_HEADER_DIALOG = "Nuevo usuario creado";
    public static final String MSG_NEW_USER_INSERTED_TEXT_DIALOG = "¡Usuario %s creado con éxito!";
    public static final String MSG_ERROR_DIALOG = "Error";
    public static final String MSG_ERROR_NEW_USER_INSERTED_HEADER_DIALOG = "Se produjeron uno o varios errores";
    public static final String MSG_ERROR_GENERA_HEADER_DIALOG = "Se produjo un error inesperado";
    public static final String MSG_ERROR_GENERA_TEXT_DIALOG = "Oops, algo salió mal. Contacta con el administrador para más información.";
    public static final String MSG_ERROR_NET_TEXT_DIALOG = "Oops, no se pudo conectar: revisa tu internet o intenta más tarde.";

    /*
     * Titulos de ventanas
     */
    public static final String TITLE_LOGIN = "Ingresar / Registrarse";
}
