package dev.gerardomarquez.chat.srvices;

import org.springframework.stereotype.Service;

import dev.gerardomarquez.chat.requests.InsertUserRequest;
import dev.gerardomarquez.chat.responses.GenericResponse;

/*
 * Interfaz que define los metodos para gestionar los usuarios
 */
@Service
public interface UsersManagerServiceI {

    /*
     * Metodo que inserta un nuevo usuario
     * @param insertUserRequest Datos del nuevo usuario
     * @return Response generico que contiene los datos del usuario creado
     */
    public GenericResponse insertOneUser(InsertUserRequest insertUserRequest);

    /*
     * Metodo que resetea la fecha en que se hizo login
     * @param token con el que se hizo login para sacar el usuario
     */
    public void resetDateByUsuer(String token);
}
