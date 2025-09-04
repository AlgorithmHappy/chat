package dev.gerardomarquez.chat.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.gerardomarquez.chat.utils.Constants;

/*
 * Clase de configuracion de spring security
 */
@Configuration
public class SecurityConfiguration {

    /*
     * Filtro que valida el token
     */
    private final JwtFilterConfiguration jwtAuthFilter;

    /*
     * Constructor para inyectar el filtro del token
     * @param Clase que filtra el token si es correcto o no
     */
    public SecurityConfiguration(JwtFilterConfiguration jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /*
     * Metodo que asegura todos los endpoints menos el del registro
     * @param Peticion http
     * @return Configuracion del filtro
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable() )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    Constants.UNSECURITY_PATH_ONE,
                    Constants.UNSECURITY_PATH_TWO
                ).permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
     * Interfaz que se tiene que ocupar como servicio para authenticar al usuario con el usuario y la contraseña
     * este tiene que envolver al repositorio
     * @param config configuracion
     * @return AuthenticationManager interfaz que autentifica al usuario con usuario y contraseña, lo debe hacer
     * spring security con esta clase
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /*
     * Para hashear las contraseñas
     * @return Objeto para hashear el password
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
