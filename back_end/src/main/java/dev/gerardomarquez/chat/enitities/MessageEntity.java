package dev.gerardomarquez.chat.enitities;

import java.time.LocalDateTime;
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

/**
 * Tabla que almacena los mensajes enviados entre usuarios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class MessageEntity {
    /**
     * UUID único del mensaje
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * ID de la conversación a la que pertenece el mensaje
     */
    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationEntity conversation;

    /**
     * Usuario que envía el mensaje
     */
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    /**
     * Usuario que recibe el mensaje
     */
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    /**
     * Contenido del mensaje (encriptado)
     */
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * Fecha y hora en que se envió el mensaje
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Indica si el receptor ya leyó el mensaje
     */
    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;
}
