package dev.gerardomarquez;

import dev.gerardomarquez.utils.Constants;
import dev.gerardomarquez.viewmanager.SceneViewManager;
import javafx.application.Application;
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
        SceneViewManager.setStage(stage);
        SceneViewManager.changeScene(Constants.PATH_RESOURCES_VIEW_LOGIN, Constants.TITLE_LOGIN);
    }

    public static void main(String[] args) {
        launch();
    }
}
