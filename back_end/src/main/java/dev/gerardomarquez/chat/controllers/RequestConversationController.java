package dev.gerardomarquez.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.gerardomarquez.chat.requests.InsertRequestConversatinRequest;
import dev.gerardomarquez.chat.responses.GenericResponse;
import dev.gerardomarquez.chat.srvices.ConversationRequestServiceI;
import dev.gerardomarquez.chat.utils.Constants;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;



/*
 * Controlado que gestiona las peticiones de las peticiones de conversacion
 */
@RestController
@RequestMapping("conversationRequest/")
public class RequestConversationController {

    /*
     * Objeto servicio para enviar las peticiones de conversacion
     */
    @Autowired
    private ConversationRequestServiceI conversationRequestService;

    /*
     * Metodo para enviar una solicitud de conversacion
     * @param authHeader Encabezado con el token de autorizacion
     * @param request Peticion con el nombre de usuario quien va a recivir la peticion
     * @return Response generico con mensaje de exito
     */
    @PostMapping("send")
    public ResponseEntity<GenericResponse> postMethodName(
        @RequestHeader("Authorization") String authHeader,
        @Valid @RequestBody InsertRequestConversatinRequest request
    ) {
        String token = authHeader.substring(Constants.BEARER.length() );
        GenericResponse response = conversationRequestService.insertOneRequestConversation(request, token);
        return ResponseEntity.ok().body(response);
    }
    
    /*
     * Metodo para obtener todas la peticiones de conversacion de un usuario
     * @param authHeader Encabezado con el token de autorizacion
     * @return Response generico con mensaje de exito
     */
    @GetMapping("getAll")
    public ResponseEntity<GenericResponse> getMethodName(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(Constants.BEARER.length() );
        GenericResponse response = conversationRequestService.getAllRequestConversation(token);
        return ResponseEntity.ok().body(response);
    }
    
}
