package dev.gerardomarquez.chat.enitities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tabla que representa las conversaciones 1 a 1 entre usuarios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class ConversationEntity {
    /**
     * UUID único de la conversación
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * ID del primer usuario en la conversación
     */
    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private UserEntity userOne;

    /**
     * ID del segundo usuario en la conversación
     */
    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private UserEntity userTwo;

    /**
     * Estado de la conversación: active o blocked
     */
    @Column(name = "status", nullable = false, columnDefinition = "varchar(20) default 'active'", length = 20)
    private String status = "active";

    /**
     * Fecha y hora de creación de la conversación
     */
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDate createdAt = LocalDate.now();

    /**
     * Fecha y hora en que la conversación fue aceptada
     */
    @Column(name = "accepted_at", nullable = true)
    private LocalDate acceptedAt;

    /*
     * Mensajes que se tienen en la conversacion
     */
    @OneToMany(mappedBy = "conversation")
    private List<MessageEntity> messages;
}
