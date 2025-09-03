package dev.gerardomarquez.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.gerardomarquez.requests.InsertUserRequest;
import dev.gerardomarquez.responses.GenericResponse;
import dev.gerardomarquez.responses.UserCreatedResponse;

public class UsersManagerServiceImplementation implements UsersManagerServiceI{

    @Override
    public List<String> insertOneUser(InsertUserRequest insertUserRequest) {
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper mapper = new ObjectMapper();

        Optional<GenericResponse<UserCreatedResponse> > genericResponse = Optional.empty();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8081/users/singUp"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(insertUserRequest ) ) )
                    .build();

            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 400){
                GenericResponse<List<String>> responseError = mapper.readValue(response.body(), GenericResponse.class);
                return responseError.getData();
            }

            genericResponse = Optional.of(
                mapper.readValue(response.body(), GenericResponse.class)
            );

            if(!genericResponse.isPresent() )
                throw new IllegalArgumentException("La data no esta presente");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        List<String> listSuccess = new ArrayList<>();
        listSuccess.add(genericResponse.get().getMessage() );

        return listSuccess;
    }

}
