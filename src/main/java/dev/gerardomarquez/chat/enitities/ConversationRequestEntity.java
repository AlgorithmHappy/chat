package dev.gerardomarquez.chat.enitities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Tabla que registra las solicitudes de conversación entre usuarios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "conversations")
public class ConversationRequestEntity {
    /**
     * UUID único de la solicitud
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Usuario que envía la solicitud de conversación
     */
    private UserEntity requesterId;

    /**
     * Usuario que recibe la solicitud
     */
    private UserEntity targetId;

    /**
     * Estado de la solicitud: pending, accepted o rejected
     */
    private String status;

    /**
     * Fecha y hora en que se creó la solicitud
     */
    private Timestamp createdAt;
}
