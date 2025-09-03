package dev.gerardomarquez.chat.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Clase de configuracion de spring security
 */
@Configuration
public class SecurityConfiguration {

    /*
     * Metodo que asegura todos los endpoints menos el del registro
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // si estás trabajando con REST, desactiva CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("users/singUp").permitAll() // tu endpoint de registro
                .anyRequest().authenticated() // todo lo demás requiere token
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Aquí agregas tu filtro de JWT antes del UsernamePasswordAuthenticationFilter
        // http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
     * Para hashear las contraseñas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
