package dev.gerardomarquez.viewmanager;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Clase que gestiona las vistas para cambiar de vistas
 */
public class SceneViewManager {

    /*
     * Stage que muestra la ventana
     */
    private static Stage stage;

    /*
     * Metodo que setea el stage, solo se ejecuta una vez al principio
     * @param s stage del metodo principal
     */
    public static void setStage(Stage s) {
        stage = s;
    }

    /*
     * Cambio de ventana
     * @param path del archivo fxml de la vista
     * @param title Titulo que llevara la ventana
     * @throw IOException excepcion que lanza si no encuentra la vista fxml
     */
    public static void changeScene(String fxmlPath, String title) throws IOException {
        Parent root = FXMLLoader.load(SceneViewManager.class.getResource(fxmlPath) );
        stage.setScene(new Scene(root) );
        stage.setResizable(Boolean.FALSE);
        stage.setTitle(title);
        stage.show();
    }

    /*
     * Metodo sobrecargado para setear el metodo que se ejecutara cuando se cierre la ventana
     * @param path del archivo fxml de la vista
     * @param title Titulo que llevara la ventana
     * @param action Metodo que se ejecutar para serrar la ventana
     * @throw IOException excepcion que lanza si no encuentra la vista fxml
     */
    public static void changeScene(String fxmlPath, String title, Runnable onClose) throws IOException {
        Parent root = FXMLLoader.load(SceneViewManager.class.getResource(fxmlPath) );
        stage.setScene(new Scene(root) );
        stage.setResizable(false);
        stage.setTitle(title);

        if (onClose != null) {
            stage.setOnCloseRequest(e -> onClose.run() );
        }

        stage.show();
    }

    /*
    * Abrir nueva ventana sin cerrar la principal
    * @param fxmlPath ruta del fxml
    * @param title título de la nueva ventana
    * @throws IOException si no encuentra el FXML
    */
    public static void openNewWindow(String fxmlPath, String title) throws IOException {
        Parent root = FXMLLoader.load(SceneViewManager.class.getResource(fxmlPath) );
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root) );
        newStage.setResizable(false);
        newStage.setTitle(title);
        newStage.show();
    }

}
