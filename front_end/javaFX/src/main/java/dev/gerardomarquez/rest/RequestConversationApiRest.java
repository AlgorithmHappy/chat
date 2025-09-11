package dev.gerardomarquez.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dev.gerardomarquez.configuration.PropertiesConfiguration;
import dev.gerardomarquez.requests.RequestConversationPost;
import dev.gerardomarquez.responses.GenericResponse;
import dev.gerardomarquez.responses.RequestConversationCreatedResponse;
import dev.gerardomarquez.responses.RequestConversationReceivedResponse;
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

    /*
     * Objeto que contiene la informacion del usuario
     */
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
     * @return Response generico con un RequestConversationCreatedResponse
     */
    public GenericResponse<RequestConversationCreatedResponse> postRequestConversationSend(RequestConversationPost request){
        String strUri = properties.get(Constants.PROPIERTIES_REST_URL)
            .concat(properties.get(Constants.PROPIERTIES_REST_PATH_CONVERSATION_REQUEST_SEND) );

        Optional<GenericResponse<RequestConversationCreatedResponse> > optionalGenericResponse = Optional.empty();

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(strUri) )
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, Constants.BEARER.concat(userInformation.getToken() ) )
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(request) ) )
                .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString() );

            if(response.statusCode() == Response.Status.OK.getStatusCode() ){
                GenericResponse<RequestConversationCreatedResponse> successResponse = mapper.readValue(
                    response.body(),
                    new TypeReference<GenericResponse<RequestConversationCreatedResponse> >() {}
                );

                return successResponse;
            }

            if(response.statusCode() == Response.Status.BAD_REQUEST.getStatusCode() ){
                GenericResponse<List<String> > genericResponse = mapper.readValue(
                    response.body(),
                    new TypeReference<GenericResponse<List<String> > >() {}
                );

                String strErrors = String.join(System.lineSeparator(), genericResponse.getData() );

                GenericResponse<RequestConversationCreatedResponse> responseError = new GenericResponse<>();
                RequestConversationCreatedResponse data = new RequestConversationCreatedResponse(
                    new String(),
                    new String(),
                    new String(),
                    new String()
                );
                responseError.setData(data);
                responseError.setSuccess(genericResponse.getSuccess() );
                responseError.setMessage(strErrors );

                return responseError;
            }

            GenericResponse<String> genericResponse = mapper.readValue(
                response.body(),
                new TypeReference<GenericResponse<String> >() {}
            );

            log.error(genericResponse.getMessage() );

            RequestConversationCreatedResponse data = new RequestConversationCreatedResponse(
                new String(),
                new String(),
                new String(),
                new String()
            );
            GenericResponse<RequestConversationCreatedResponse> errorResponse = new GenericResponse<>();
            errorResponse.setData(data );
            errorResponse.setMessage(genericResponse.getMessage() );
            errorResponse.setSuccess(Boolean.FALSE);

            optionalGenericResponse = Optional.of(errorResponse);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error(e.getMessage() );

            RequestConversationCreatedResponse data = new RequestConversationCreatedResponse(
                    new String(),
                    new String(),
                    new String(),
                    new String()
                );
            GenericResponse<RequestConversationCreatedResponse> genericResponse = new GenericResponse<>();
            genericResponse.setData(data );
            genericResponse.setMessage( e.getMessage() );
            genericResponse.setSuccess(Boolean.FALSE);

            log.error(genericResponse);

            optionalGenericResponse = Optional.of(genericResponse);
        }

        if(optionalGenericResponse.isEmpty() ){
            log.error(Constants.MSG_ERROR_EMPTY_OPTIONAL);

            RequestConversationCreatedResponse data = new RequestConversationCreatedResponse(
                    new String(),
                    new String(),
                    new String(),
                    new String()
                );
            GenericResponse<RequestConversationCreatedResponse> genericResponse = new GenericResponse<>();
            genericResponse.setData(data);
            genericResponse.setMessage(Constants.MSG_ERROR_GENERAL );
            genericResponse.setSuccess(Boolean.FALSE);

            optionalGenericResponse = Optional.of(genericResponse);
        }

        return optionalGenericResponse.get();
    }

    /*
     * Metodo que realiza una peticion get para devolver todas las peticiones de conversacion del usuario actual "requester"
     * @return Response generico con una lista de RequestConversationCreatedResponse
     */
    public GenericResponse<List<RequestConversationCreatedResponse> > getAll(){
        String strUri = properties.get(Constants.PROPIERTIES_REST_URL)
            .concat(properties.get(Constants.PROPIERTIES_REST_PATH_CONVERSATION_REQUEST_GET_ALL) );

        String error = new String();

        Optional<URI> optUri = Optional.empty();
        try {
            optUri = Optional.of(new URI(strUri) );
        } catch (URISyntaxException e) {
            log.error(e.getMessage() );
            error = e.getMessage();
        }

        if(optUri.isEmpty() ){
            List<RequestConversationCreatedResponse> listErrorRequestConversation = new ArrayList<>();

            GenericResponse<List<RequestConversationCreatedResponse> > errorResponse = new GenericResponse<>(
                Boolean.FALSE,
                error,
                listErrorRequestConversation
            );

            return errorResponse;
        }


        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(optUri.get() )
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, Constants.BEARER.concat(userInformation.getToken() ) )
            .GET()
            .build();

        Optional<HttpResponse<String> > optResponse = Optional.empty();
        try {
            optResponse = Optional.of(client.send(httpRequest, HttpResponse.BodyHandlers.ofString() ) );
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage() );
            error = e.getMessage();
        }

        if(optResponse.isEmpty() ){
            List<RequestConversationCreatedResponse> listErrorRequestConversation = new ArrayList<>();

            GenericResponse<List<RequestConversationCreatedResponse> > errorResponse = new GenericResponse<>(
                Boolean.FALSE,
                error,
                listErrorRequestConversation
            );

            return errorResponse;
        }
        
        try {
            if(optResponse.get().statusCode() == Response.Status.OK.getStatusCode() ){
                GenericResponse<List<RequestConversationCreatedResponse> > successResponse;
                
                successResponse = mapper.readValue(
                    optResponse.get().body(),
                    new TypeReference<GenericResponse<List<RequestConversationCreatedResponse> > >() {}
                );

                return successResponse;
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage() );
            error = e.getMessage();
            List<RequestConversationCreatedResponse> listErrorRequestConversation = new ArrayList<>();
            GenericResponse<List<RequestConversationCreatedResponse> > errorResponse = new GenericResponse<>(
                Boolean.FALSE,
                error,
                listErrorRequestConversation
            );

            return errorResponse;
        }

        List<RequestConversationCreatedResponse> listErrorRequestConversation = new ArrayList<>();
        GenericResponse<List<RequestConversationCreatedResponse> > errorResponse = new GenericResponse<>(
            Boolean.FALSE,
            Constants.MSG_ERROR_GENERAL,
            listErrorRequestConversation
        );
        return errorResponse;
    }

    /*
     * Metodo que borra una peticion de conversacion
     * @param id de la peticion que se quiere borrar
     */
    public void deleteOne(String id){
        String strUri = properties.get(Constants.PROPIERTIES_REST_URL)
            .concat(properties.get(Constants.PROPIERTIES_REST_PATH_CONVERSATION_REQUEST_DELETE_ONE)
            .concat(id)
        );

        Optional<URI> optUri = Optional.empty();
        try {
            optUri = Optional.of(new URI(strUri) );
        } catch (URISyntaxException e) {
            log.error(e.getMessage() );
            return;
        }

        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(optUri.get() )
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, Constants.BEARER.concat(userInformation.getToken() ) )
            .DELETE()
            .build();

        try {
            client.send(httpRequest, HttpResponse.BodyHandlers.ofString() );
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage() );
        }
    }

    /*
     * Metodo que realiza una peticion get para devolver todas las peticiones de conversacion del usuario "target"
     * @return Response generico con una lista de RequestConversationReceivedResponse
     */
    public GenericResponse<List<RequestConversationReceivedResponse> > getAllByTarget(){
        String strUri = properties.get(Constants.PROPIERTIES_REST_URL)
            .concat(properties.get(Constants.PROPIERTIES_REST_PATH_CONVERSATION_REQUEST_GET_ALL_RECEIVED) );

        String error = new String();

        Optional<URI> optUri = Optional.empty();
        try {
            optUri = Optional.of(new URI(strUri) );
        } catch (URISyntaxException e) {
            log.error(e.getMessage() );
            error = e.getMessage();
        }

        if(optUri.isEmpty() ){
            List<RequestConversationReceivedResponse> listErrorRequestConversation = new ArrayList<>();

            GenericResponse<List<RequestConversationReceivedResponse> > errorResponse = new GenericResponse<>(
                Boolean.FALSE,
                error,
                listErrorRequestConversation
            );

            return errorResponse;
        }


        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(optUri.get() )
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, Constants.BEARER.concat(userInformation.getToken() ) )
            .GET()
            .build();

        Optional<HttpResponse<String> > optResponse = Optional.empty();
        try {
            optResponse = Optional.of(client.send(httpRequest, HttpResponse.BodyHandlers.ofString() ) );
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage() );
            error = e.getMessage();
        }

        if(optResponse.isEmpty() ){
            List<RequestConversationReceivedResponse> listErrorRequestConversation = new ArrayList<>();

            GenericResponse<List<RequestConversationReceivedResponse> > errorResponse = new GenericResponse<>(
                Boolean.FALSE,
                error,
                listErrorRequestConversation
            );

            return errorResponse;
        }
        
        try {
            if(optResponse.get().statusCode() == Response.Status.OK.getStatusCode() ){
                GenericResponse<List<RequestConversationReceivedResponse> > successResponse;
                
                successResponse = mapper.readValue(
                    optResponse.get().body(),
                    new TypeReference<GenericResponse<List<RequestConversationReceivedResponse> > >() {}
                );

                return successResponse;
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage() );
            error = e.getMessage();
            List<RequestConversationReceivedResponse> listErrorRequestConversation = new ArrayList<>();
            GenericResponse<List<RequestConversationReceivedResponse> > errorResponse = new GenericResponse<>(
                Boolean.FALSE,
                error,
                listErrorRequestConversation
            );

            return errorResponse;
        }

        List<RequestConversationReceivedResponse> listErrorRequestConversation = new ArrayList<>();
        GenericResponse<List<RequestConversationReceivedResponse> > errorResponse = new GenericResponse<>(
            Boolean.FALSE,
            Constants.MSG_ERROR_GENERAL,
            listErrorRequestConversation
        );
        return errorResponse;
    }
}
