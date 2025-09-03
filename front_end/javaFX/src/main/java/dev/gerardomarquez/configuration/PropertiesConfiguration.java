package dev.gerardomarquez.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import dev.gerardomarquez.utils.Constants;

/*
 * Clase que contiene la configuracion de las propiedades, es un singleton
 */
public class PropertiesConfiguration {

    /*
     * Instancia unica de las propiedades
     */
    private static PropertiesConfiguration instance;

    /*
     * Instancia de las properties
     */
    private final Properties properties;

    /*
     * Constructor para setear el singleton con las propiedades
     */
    private PropertiesConfiguration() {
        properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream(Constants.PATH_RESOURCES_PROPERTIES) ) {
            if (input == null) {
                throw new RuntimeException(Constants.ERROR_PROPERTIES_NOT_FOUND);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(Constants.ERROR_PROPERTIES_NOT_UPLOAD, e);
        }
    }

    /*
     * Metodo que devuelve la unica instancia de las propiedades
     * @return Mi clase singleton que contiene las propiedades
     */
    public static synchronized PropertiesConfiguration getInstance() {
        if (instance == null) {
            instance = new PropertiesConfiguration();
        }
        return instance;
    }

    /*
     * Obtener propiedad como String
     * @param key Llave de la propiedad
     * @return Valor de la propiedad
     */
    public String get(String key) {
        return properties.getProperty(key);
    }

    /*
     * Obtener propiedad como Integer
     * @param key Llave de la propiedad
     * @return Valor de la propiedad
     */
    public Integer getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    /*
     * Obtener propiedad como Boolean
     * @param key Llave de la propiedad
     * @return Valor de la propiedad
     */
    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }
}
