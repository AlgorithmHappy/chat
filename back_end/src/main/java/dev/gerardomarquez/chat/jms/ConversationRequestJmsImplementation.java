package dev.gerardomarquez.chat.jms;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.gerardomarquez.chat.requests.SendQueueConversationRequest;
import dev.gerardomarquez.chat.utils.Constants;

/*
 * {@Inheritdock}
 */
@Service
public class ConversationRequestJmsImplementation implements ConversationRequestJmsI {

    /*
     * Objeto para enviar las colas
     */
    private final JmsTemplate jmsTemplate;

    /*
     * Objeto para convertir a json
     */
    private ObjectMapper objectMapper;

    @Value("${app.queue.requests}")
    private String queueName;

    /*
     * Constructor para injectar el JmsTemplate
     * @param jmsTemplate Objeto para enviar las colas
     */
    public ConversationRequestJmsImplementation(
        JmsTemplate jmsTemplate
    ){
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = new ObjectMapper();
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public void sendQueueToUsuer(SendQueueConversationRequest requestQueue, String usernameId) {
        String strRequest = new String();
        try {
            strRequest = objectMapper.writeValueAsString(requestQueue);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        jmsTemplate.convertAndSend(queueName, strRequest, msg -> {
            msg.setStringProperty(Constants.JMS_PROPERTI_KEY_ID_USER, usernameId); // propiedad custom
            return msg;
        });
    
    }

    
}
