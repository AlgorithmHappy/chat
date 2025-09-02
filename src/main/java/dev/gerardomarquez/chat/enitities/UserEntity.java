package dev.gerardomarquez.chat.enitities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tabla que almacena la información principal de los usuarios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    /**
     * UUID único del usuario
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Nombre de usuario único
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Contraseña encriptada con hash seguro
     */
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    /**
     * Indica si el usuario tiene una sesión activa
     */
    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default false")
    private Boolean isActive = false;

    /**
     * Fecha y hora de creación del usuario
     */
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDate createdAt = LocalDate.now();
}
