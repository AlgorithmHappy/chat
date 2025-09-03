package dev.gerardomarquez.controllers;

import java.util.List;

import dev.gerardomarquez.requests.InsertUserRequest;
import dev.gerardomarquez.responses.GenericResponse;
import dev.gerardomarquez.services.UsersManagerServiceI;
import dev.gerardomarquez.services.UsersManagerServiceImplementation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    private UsersManagerServiceI userManagerService;

    @FXML
    private TextField textFieldUser;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private Button buttonSignUp;

    @FXML
    private Button buttonSignIn;

    @FXML
    public void initialize() {
        userManagerService = new UsersManagerServiceImplementation();

        buttonSignUp.setOnAction(e -> {
            String username = textFieldUser.getText();
            String password = textFieldPassword.getText();

            InsertUserRequest insertUserRequest = InsertUserRequest.builder()
                .nickName(username)
                .password(password)
                .build();

            List<String> response = userManagerService.insertOneUser(insertUserRequest);

            if(!response.get(0).equals("Operación correcta.") ){
                String error = String.join(System.lineSeparator(), response);

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Se produjo un error");
                alert.setContentText(error);

                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmacion");
                alert.setHeaderText("Nuevo usuario");
                alert.setContentText("Se creo exitosamente el usuario");

                alert.showAndWait();
            }
        });
    }
}
