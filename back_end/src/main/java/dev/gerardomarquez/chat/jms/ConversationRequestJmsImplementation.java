package dev.gerardomarquez.chat.jms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.gerardomarquez.chat.requests.DeleteConversationToTarget;
import dev.gerardomarquez.chat.requests.SendQueueConversationRequest;
import dev.gerardomarquez.chat.utils.Constants;
import lombok.extern.log4j.Log4j2;

/*
 * {@Inheritdock}
 */
@Service
@Log4j2
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
     * @param Objeto para convertir a json
     */
    public ConversationRequestJmsImplementation(
        JmsTemplate jmsTemplate,
        ObjectMapper objectMapper
    ){
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public void sendRequestConversationQueueToUsuer(SendQueueConversationRequest requestQueue, String usernameId) {
        String strRequest = new String();
        try {
            strRequest = objectMapper.writeValueAsString(requestQueue);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage() );
        }

        jmsTemplate.convertAndSend(queueName, strRequest, msg -> {
            msg.setStringProperty(Constants.JMS_PROPERTI_KEY_ID_USER, usernameId);
            return msg;
        });
    
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public void sendRequestConversationQueueToUsuer(DeleteConversationToTarget requestConversationId, String targetId) {
        String strRequest = new String();
        try {
            strRequest = objectMapper.writeValueAsString(requestConversationId);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage() );
        }
        jmsTemplate.convertAndSend(queueName, strRequest, msg -> {
            msg.setStringProperty(Constants.JMS_PROPERTI_KEY_ID_USER, targetId);
            return msg;
        });
    }

}
