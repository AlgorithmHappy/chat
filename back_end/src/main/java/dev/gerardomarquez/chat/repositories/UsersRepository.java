package dev.gerardomarquez.chat.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.chat.enitities.UserEntity;


/*
 * Repositorio que se conecta a la base de datos para gestionar a los usuarios
 */
@Repository
public interface UsersRepository extends JpaRepository<UserEntity, UUID> {

    /*
     * Devuelve al usuario con una sola entidad ya que es unico
     * @param username Nombre de usuario
     * @return Optiona de la entidad usuario
     */
    public Optional<UserEntity> findByUsername(String username);
}
