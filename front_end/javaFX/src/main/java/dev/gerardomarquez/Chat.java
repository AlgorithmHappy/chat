package dev.gerardomarquez;

import dev.gerardomarquez.utils.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Clase principal
 */
public class Chat extends Application {

    /*
     * Inicializa la primera ventana del programa
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_RESOURCES_VIEW_LOGIN) );
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setTitle(Constants.TITLE_LOGIN);
        stage.setScene(scene);
        stage.setResizable(Boolean.FALSE);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
