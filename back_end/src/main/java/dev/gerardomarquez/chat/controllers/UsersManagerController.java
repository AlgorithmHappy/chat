package dev.gerardomarquez.chat.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.gerardomarquez.chat.requests.InsertUserRequest;
import dev.gerardomarquez.chat.responses.GenericResponse;
import dev.gerardomarquez.chat.srvices.UsersManagerServiceI;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * Controlador que gestionara los usuarios, logins y registros
 */
@RestController
@RequestMapping("users/")
public class UsersManagerController {

    /*
     * Servicio que gestiona los usuarios
     */
    @Autowired
    private UsersManagerServiceI usersManagerService;

    /*
     * Registro de usuario
     */
    @PostMapping("singUp")
    public ResponseEntity<GenericResponse> postMethodName(@Valid @RequestBody InsertUserRequest request) {
        GenericResponse response = usersManagerService.insertOneUser(request);
        
        return ResponseEntity.created(null).body(response);
    }
    
}
