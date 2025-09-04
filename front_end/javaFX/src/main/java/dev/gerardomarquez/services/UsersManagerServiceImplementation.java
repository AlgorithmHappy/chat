package dev.gerardomarquez.services;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dev.gerardomarquez.configuration.PropertiesConfiguration;
import dev.gerardomarquez.requests.InsertUserRequest;
import dev.gerardomarquez.responses.GenericResponse;
import dev.gerardomarquez.responses.UserCreatedResponse;
import dev.gerardomarquez.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.extern.log4j.Log4j2;

/*
* {@inheritDoc}
*/
@Log4j2
public class UsersManagerServiceImplementation implements UsersManagerServiceI{

    /*
     * Objeto que contiene las propiedades, la url y el path de consumo
     */
    private PropertiesConfiguration properties;

    /*
     * Constructor que inicializa las variables a ocupar
     */
    public UsersManagerServiceImplementation(){
        properties = PropertiesConfiguration.getInstance();
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public Alert insertOneUser(String user, String password) {
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule() );
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String strUri = properties.get(Constants.PROPIERTIES_REST_URL)
            .concat(properties.get(Constants.PROPIERTIES_REST_PATH_USERS_SIGNUP) );

        Optional<Alert> alert = Optional.empty();

        InsertUserRequest insertUserRequest = InsertUserRequest.builder()
            .nickName(user)
            .password(password)
            .build();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(strUri))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(insertUserRequest ) ) )
                .build();

            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == Response.Status.CREATED.getStatusCode() ){
                GenericResponse<UserCreatedResponse> genericResponse = mapper.readValue(
                    response.body(),
                    new TypeReference<GenericResponse<UserCreatedResponse>>() {}
                );
                Alert alertSucccess = new Alert(AlertType.CONFIRMATION);
                alertSucccess.setTitle(Constants.MSG_CONFIRMATION_DIALOG);
                alertSucccess.setHeaderText(Constants.MSG_NEW_USER_INSERTED_HEADER_DIALOG);
                alertSucccess.setContentText(
                    String.format(Constants.MSG_NEW_USER_INSERTED_TEXT_DIALOG, genericResponse.getData().getNickName() )
                );
                alert = Optional.of(alertSucccess);
            }

            if(response.statusCode() == Response.Status.BAD_REQUEST.getStatusCode() ){
                GenericResponse<List<String>> responseError = mapper.readValue(
                    response.body(),
                    new TypeReference<GenericResponse<List<String> > >() {}
                );

                log.error(responseError);

                String allErrors = String.join(System.lineSeparator(), responseError.getData() );
                Alert alertError = new Alert(AlertType.ERROR);
                alertError.setTitle(Constants.MSG_ERROR_DIALOG);
                alertError.setHeaderText(Constants.MSG_ERROR_NEW_USER_INSERTED_HEADER_DIALOG);
                alertError.setContentText(allErrors);
                alert = Optional.of(alertError);
            }

            if(response.statusCode() == Constants.HTTP_STATUS_CODE_UNPROCESSABLE_ENTITY ){
                GenericResponse<String> responseError = mapper.readValue(
                    response.body(),
                    new TypeReference<GenericResponse<String> >() {}
                );

                log.error(responseError);

                Alert alertError = new Alert(AlertType.ERROR);
                alertError.setTitle(Constants.MSG_ERROR_DIALOG);
                alertError.setHeaderText(Constants.MSG_ERROR_NEW_USER_INSERTED_HEADER_DIALOG);
                alertError.setContentText(Constants.MSG_ERROR_NEW_USER_UNIQUE_CONSTRAINT_DIALOG);
                alert = Optional.of(alertError);
            }

        } catch (ConnectException e){
            log.error(e);

            Alert alertError = new Alert(AlertType.ERROR);
            alertError.setTitle(Constants.MSG_ERROR_DIALOG);
            alertError.setHeaderText(Constants.MSG_ERROR_NEW_USER_INSERTED_HEADER_DIALOG);
            alertError.setContentText(Constants.MSG_ERROR_NET_TEXT_DIALOG);
            alert = Optional.of(alertError);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error(e.getMessage() );
        }

        if(alert.isPresent() )
            return alert.get();
        else {
            log.error(Constants.MSG_ERROR_LOG_ALERT_DIALOG_IS_NOT_PRESENT);
            Alert alertGeneralError = new Alert(AlertType.ERROR);
            alertGeneralError.setAlertType(AlertType.ERROR);
            alertGeneralError.setTitle(Constants.MSG_ERROR_GENERA_HEADER_DIALOG);
            alertGeneralError.setContentText(Constants.MSG_ERROR_GENERA_TEXT_DIALOG);
            return alertGeneralError;
        }
    }

}
