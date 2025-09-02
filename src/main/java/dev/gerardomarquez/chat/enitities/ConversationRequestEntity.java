package dev.gerardomarquez.chat.enitities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Tabla que registra las solicitudes de conversación entre usuarios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "conversation_requests")
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
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private UserEntity requester;

    /**
     * Usuario que recibe la solicitud
     */
    @ManyToOne
    @JoinColumn(name = "target_id", nullable = false)
    private UserEntity target;

    /**
     * Estado de la solicitud: pending, accepted o rejected
     */
    @Column(name = "status", nullable = false)
    private String status = "pending";

    /**
     * Fecha y hora en que se creó la solicitud
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
