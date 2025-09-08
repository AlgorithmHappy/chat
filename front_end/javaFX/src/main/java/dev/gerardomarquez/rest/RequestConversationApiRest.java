package dev.gerardomarquez.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dev.gerardomarquez.configuration.PropertiesConfiguration;
import dev.gerardomarquez.requests.RequestConversationPost;
import dev.gerardomarquez.responses.GenericResponse;
import dev.gerardomarquez.utils.Constants;
import dev.gerardomarquez.utils.UserInformation;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;

/*
 * Clase que gestiona las peticiones restfull al backend
 */
@Log4j2
public class RequestConversationApiRest {

    /*
     * Para hacer las peticiones rest
     */
    private HttpClient client;

    /*
     * Para parsear los objetos de json a objetos java y viceversa
     */
    private ObjectMapper mapper;

    /*
     * Objeto que contiene las propiedades, la url y el path de consumo
     */
    private PropertiesConfiguration properties;

    private UserInformation userInformation;

    /*
     * Constructor que inicializa todo
     */
    public RequestConversationApiRest(){
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule() );
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.properties = PropertiesConfiguration.getInstance();
        this.userInformation = UserInformation.getInstancia();
    }

    /*
     * Metodo que realiza una peticion post a /conversationRequest/send
     * @param request Request con los atributos para hacer la peticion
     * @return Response generico con un string
     */
    public GenericResponse<String> postRequestConversationSend(RequestConversationPost request){
        String strUri = properties.get(Constants.PROPIERTIES_REST_URL)
            .concat(properties.get(Constants.PROPIERTIES_REST_PATH_CONVERSATION_REQUEST_SEND) );

        Optional<GenericResponse<String> > optionalGenericResponse = Optional.empty();

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(strUri) )
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, Constants.BEARER.concat(userInformation.getToken() ) )
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(request) ) )
                .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString() );

            if(response.statusCode() == Response.Status.BAD_REQUEST.getStatusCode() ){
                GenericResponse<List<String> > genericResponse = mapper.readValue(
                    response.body(),
                    new TypeReference<GenericResponse<List<String> > >() {}
                );

                String strErrors = String.join(System.lineSeparator(), genericResponse.getData() );

                GenericResponse<String> responseError = new GenericResponse<>();
                responseError.setData(strErrors);
                responseError.setSuccess(genericResponse.getSuccess() );
                responseError.setMessage(genericResponse.getMessage() );

                return responseError;
            }
            
            GenericResponse<String> genericResponse = mapper.readValue(
                response.body(),
                new TypeReference<GenericResponse<String> >() {}
            );

            optionalGenericResponse = Optional.of(genericResponse);
            
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error(e.getMessage() );

            GenericResponse<String> genericResponse = new GenericResponse<String>();
            genericResponse.setData(e.getMessage() );
            genericResponse.setMessage(new String() );
            genericResponse.setSuccess(Boolean.FALSE);

            log.error(genericResponse);

            optionalGenericResponse = Optional.of(genericResponse);
        }

        if(optionalGenericResponse.isEmpty() ){
            GenericResponse<String> genericResponse = new GenericResponse<String>();
            genericResponse.setData(new String() );
            genericResponse.setMessage(new String() );
            genericResponse.setSuccess(Boolean.FALSE);

            log.error("Optional vacio");
        }

        return optionalGenericResponse.get();
    }
}
