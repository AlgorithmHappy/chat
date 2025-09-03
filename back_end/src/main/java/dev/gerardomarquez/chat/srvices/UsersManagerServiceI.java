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
}
