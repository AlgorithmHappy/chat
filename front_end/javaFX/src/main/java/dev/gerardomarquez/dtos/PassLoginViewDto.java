package dev.gerardomarquez.dtos;

import java.util.Optional;

import javafx.scene.control.Alert;
import lombok.Builder;
import lombok.Getter;

/*
 * Dto que indica si va a cambiar de escena si el login fue correcto
 */
@Builder
@Getter
public class PassLoginViewDto {
    /*
     * Nuevo titulo de ventana en el caso de que el login haya sido exitoso
     */
    private Optional<String> newWindowTitle;

    /*
     * Nuevo path del archivo fxml de la ventana en el caso de que el login haya sido exitoso
     */
    private Optional<String> newFxmlPath;

    /*
     * Si fue exitoso el login
     */
    private Boolean success;

    /*
     * Alerta en el caso de que haya habido un error
     */
    private Optional<Alert> alert;

    /*
     * Token en el caso de que el login haya sido correcto
     */
    private Optional<String> token;
}
