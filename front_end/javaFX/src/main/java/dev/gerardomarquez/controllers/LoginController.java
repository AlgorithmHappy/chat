package dev.gerardomarquez.controllers;

import dev.gerardomarquez.services.UsersManagerServiceI;
import dev.gerardomarquez.services.UsersManagerServiceImplementation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/*
 * Clase que controlara la vista de Login.fxml
 */
public class LoginController {

    /*
     * Servicio para mandar la peticion "POST" del registro de usuario
     */
    private UsersManagerServiceI userManagerService;

    /*
     * Input texto donde el usuario ingresa su nombre de usuario
     */
    @FXML
    private TextField textFieldUser;

    /*
     * Input texto donde el usuario ingresa su contraseña
     */
    @FXML
    private TextField textFieldPassword;

    /*
     * Boton para registrar al usuario
     */
    @FXML
    private Button buttonSignUp;

    /*
     * Boton para ingresar
     */
    @FXML
    private Button buttonSignIn;

    /*
     * Metodo de java FX para incializar las variables
     */
    @FXML
    public void initialize() {
        userManagerService = new UsersManagerServiceImplementation();

        buttonSignUp.setOnAction(
            e -> {
                Alert alert = userManagerService.insertOneUser(
                    textFieldUser.getText(),
                    textFieldPassword.getText()
                );
                alert.showAndWait();
            }
        );
    }
}
