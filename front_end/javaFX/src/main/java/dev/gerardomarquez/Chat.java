package dev.gerardomarquez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Clase principal
 */
public class Chat extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/gerardomarquez/Login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false); // evita redimensionar
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
