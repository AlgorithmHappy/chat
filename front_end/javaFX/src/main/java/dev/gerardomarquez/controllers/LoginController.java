package dev.gerardomarquez.controllers;

import java.io.IOException;

import dev.gerardomarquez.dtos.PassLoginViewDto;
import dev.gerardomarquez.services.UsersManagerServiceI;
import dev.gerardomarquez.services.UsersManagerServiceImplementation;
import dev.gerardomarquez.viewmanager.SceneViewManager;
import dev.gerardomarquez.utils.UserInformation;

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
     * Objeto al que le setearemos el token
     */
    private UserInformation userInformation;

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

        userInformation = UserInformation.getInstancia();
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

    /*
     * Metodo que se ejecuta cuando se presiona el boton de signIn
     */
    @FXML
    private void buttonSignInOnAction(){
        PassLoginViewDto passLoginView = userManagerService.login(
            textFieldUser.getText(),
            textFieldPassword.getText()
        );

        if(!passLoginView.getSuccess() ){
            passLoginView.getAlert().get().show();
            return;
        }

        try {
            userInformation.setToke(passLoginView.getToken().get() );
            SceneViewManager.changeScene(
                passLoginView.getNewFxmlPath().get(),
                passLoginView.getNewWindowTitle().get(),
                () -> {
                    userManagerService.logout(passLoginView.getToken().get() );
                }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
