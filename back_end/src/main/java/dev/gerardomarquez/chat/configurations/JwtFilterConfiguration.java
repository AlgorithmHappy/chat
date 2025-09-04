package dev.gerardomarquez.chat.configurations;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.gerardomarquez.chat.utils.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Clase de filtrado para solo los que tengan el token correcto
 */
@Component
public class JwtFilterConfiguration extends OncePerRequestFilter {
    
    /*
     * El que firma y deserializa el token
     */
    private final JwtConfiguration jwtService;

    /*
     * Constructor inyectando la dependencia
     */
    public JwtFilterConfiguration(JwtConfiguration jwtService) {
        this.jwtService = jwtService;
    }

    /*
     * Filtro para validar el token
     */
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(Constants.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(Constants.BEARER) ) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(Constants.BEARER.length() );
        String username = jwtService.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            new User(username, "", Collections.emptyList() ),
                            null,
                            Collections.emptyList()
                    );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request) );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
