package dev.gerardomarquez.chat.repositories;

import org.springframework.stereotype.Repository;

import dev.gerardomarquez.chat.enitities.ConversationRequestEntity;
import dev.gerardomarquez.chat.enitities.UserEntity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repositorio que se conecta a la base de datos para gestionar los request de las conversaciones
 */
@Repository
public interface ConversationRequestRepository extends JpaRepository<ConversationRequestEntity, UUID>{

    /*
     * Metodo que encuentra todos los request del un usuario "requester" enviados a otro usuario "target"
     * @param requester Variable con el uuid del usuario quien hace el request
     * @param target Variable con el uuid del usuario a quien le llega el request
     */
    public List<ConversationRequestEntity> findAllByRequesterAndTarget(UserEntity requester, UserEntity target);
}
