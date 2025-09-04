package dev.gerardomarquez.chat.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.gerardomarquez.chat.configurations.JwtConfiguration;
import dev.gerardomarquez.chat.requests.InsertUserRequest;
import dev.gerardomarquez.chat.responses.GenericResponse;
import dev.gerardomarquez.chat.srvices.UsersManagerServiceI;
import dev.gerardomarquez.chat.utils.Constants;
import jakarta.validation.Valid;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
     * Objeto de spring para autenticar al usuario
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /*
     * Para crear el token y deserializar el token
     */
    @Autowired
    private JwtConfiguration jwtService;

    @Autowired
    private MessageSource messageSource;

    /*
     * Registro de usuario
     * @param request Request con el usuario y la contraseña
     * @return Response generico con el nombre de usuario y la fecha de creacion
     */
    @PostMapping("singUp")
    public ResponseEntity<GenericResponse> signUp(@Valid @RequestBody InsertUserRequest request) {
        GenericResponse response = usersManagerService.insertOneUser(request);
        
        return ResponseEntity.created(null).body(response);
    }
    
    /*
     * Login del usuario
     * @param request Request con el usuario y la contraseña
     * @return Response generico con el nombre de usuario y la fecha de creacion
     */
    @PostMapping("singIn")
    public ResponseEntity<GenericResponse> signIn(@Valid @RequestBody InsertUserRequest request) {        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNickName(), request.getPassword() ) );
        String token = jwtService.generateToken(request.getNickName() );

        GenericResponse response = GenericResponse.builder()
            .success(Boolean.TRUE)
            .data(token)
            .message(messageSource.getMessage(Constants.MSG_SUCCESS, null, Locale.getDefault() ) )
            .build();

        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("prueba")
    public String getMethodName() {
        return "prueba";
    }
    
}
