package dev.gerardomarquez.chat.configurations;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.chat.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*
 * Implementacion de JWT para devolver el token
 */
@Service
public class JwtConfiguration {

    /*
     * Secret para firmar el token
     */
    @Value("${jwt.secret}")
    private String secret;

    /*
     * Horas parametrizadas para la expiracion del token
     */
    @Value("${jwt.hours.time}")
    private Integer hourExpiration;

    /*
     * Metodo que convierte las horas a milisegundos
     * @return Milisegundos de las horas parametrizadas
     */
    private Long expirationTime(){
        return Constants.TIME_EXPIRATED_CONVERTER * hourExpiration;
    }

    /*
     * Crea el token con el usuario
     * @param Nombre de usuario
     * @return Token creado a partir del usuario
     */
    public String generateToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret) );
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(this.expirationTime() );

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now) )
                .expiration(Date.from(expiry) )
                .signWith(key)
                .compact();
    }

    /*
     * Extrae el nombre del usuario del token
     * @param token
     * @return Nombre de usuario registrado
     */
    public String extractUsername(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret) );
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }
}
