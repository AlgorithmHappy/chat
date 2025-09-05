package dev.gerardomarquez.chat.configurations;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.gerardomarquez.chat.responses.GenericResponse;
import dev.gerardomarquez.chat.utils.Constants;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/*
 * Clase que realiza un limite de peticiones para que no hagan ataques de
 * denegacion de servicio
 */
@Component
@Log4j2
public class RateLimitingFilterConfiguration implements Filter {

    /*
     * Archivo properties que contiene los mensajes de error
     */
    @Autowired
    private MessageSource messageSource;

    @Value("${rate.limit.capacity}")
    private Integer capacity;

    @Value("${rate.limit.refill.capacity}")
    private Integer refillCapacity;

    @Value("${rate.limit.refill.minutes}")
    private Integer refillMinutes;

    /*
     * Objeto que hace de buffer para las peticiones
     */
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    /*
     * {@inheritDoc}
     * Metodo que se ejecuta al llegar la peticion
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = httpRequest.getRemoteAddr();

        String uri = httpRequest.getRequestURI();

        if(!(uri.equals(Constants.RATE_LIMIT_PATH_ONE) || uri.equals(Constants.RATE_LIMIT_PATH_TWO) ) ){
            chain.doFilter(request, response);
        }

        Bucket bucket = buckets.computeIfAbsent(ip, this::newBucket);

        if (bucket.tryConsume(1) ) {
            chain.doFilter(request, response);
        } else {
            Object[] interpoletion = { refillMinutes };
            String data = messageSource.getMessage(Constants.MSG_RATE_LIMIT, interpoletion, Locale.getDefault() );

            ObjectMapper mapper = new ObjectMapper();

            GenericResponse errorResponse = GenericResponse.builder()
                .data(data)
                .success(Boolean.FALSE)
                .message(Constants.MSG_EXCEPTION_RATE_LIMIT)
                .build();

            log.error(errorResponse);

            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpResponse.setCharacterEncoding(StandardCharsets.UTF_8.name() );
            httpResponse.setStatus(Constants.HTTP_STATUS_CODE_RATE_LIMIT);
            httpResponse.getWriter().write(mapper.writeValueAsString(errorResponse) );
            
            return;
        }
    }

    /*
     * Crea un limite de n cantidad de peticiones seguidas con un reposo de k minutos para agregar
     * una peticion mas al limite, estos valores estan parametrizados desde el properties
     * @param ip Ip del cliente que realiza la peticion
     * @return Bucket bucket con la configuracion de n peticiones y un refresco de k peticiones
     * cada j minutos
     */
    private Bucket newBucket(String ip) {
        Bandwidth limit = Bandwidth.classic(
            capacity,
            Refill.greedy(
                refillCapacity,
                Duration.ofMinutes(refillMinutes)
            )
        );
        return Bucket.builder().addLimit(limit).build();
    }

}
