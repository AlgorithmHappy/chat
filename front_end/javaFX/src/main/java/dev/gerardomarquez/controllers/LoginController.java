package dev.gerardomarquez.controllers;

import dev.gerardomarquez.services.UsersManagerServiceI;
import dev.gerardomarquez.services.UsersManagerServiceImplementation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
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
     * Checkbox para hacer visible o oculto la contraseña
     */
    @FXML
    private CheckBox checkBoxShowPassword;

    /*
     * Para poner la contraseña oculta
     */
    @FXML
    private PasswordField passwordField;

    /*
     * Metodo de java FX para incializar las variables
     */
    @FXML
    public void initialize() {
        userManagerService = new UsersManagerServiceImplementation();

        textFieldPassword.managedProperty().bind(checkBoxShowPassword.selectedProperty() );
        textFieldPassword.visibleProperty().bind(checkBoxShowPassword.selectedProperty() );

        passwordField.managedProperty().bind(checkBoxShowPassword.selectedProperty().not() );
        passwordField.visibleProperty().bind(checkBoxShowPassword.selectedProperty().not() );

        textFieldPassword.textProperty().bindBidirectional(passwordField.textProperty() );

    }

    /*
     * Metodo que se ejecuta cuando se presiona el boton de signUp
     */
    @FXML
    private void buttonSignUpOnAction(){
        Alert alert = userManagerService.insertOneUser(
                textFieldUser.getText(),
                textFieldPassword.getText()
            );
        alert.showAndWait();
    }
}
