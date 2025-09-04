package dev.gerardomarquez.chat.configurations;

import java.io.IOException;
import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.glassfish.jaxb.runtime.v2.runtime.reflect.opt.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;

import dev.gerardomarquez.chat.exceptions.RateLimitExceededException;
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

/*
 * Clase que realiza un limite de peticiones para que no hagan ataques de
 * denegacion de servicio
 */
@Component
public class RateLimitingFilter implements Filter {

    /*
     * Log que escribira los errores
     */
    private static final Logger logger = LoggerFactory.getLogger(RateLimitingFilter.class);

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

        if(!uri.equals(Constants.RATE_LIMIT_PATH_ONE) ){
            chain.doFilter(request, response);
        }

        Bucket bucket = buckets.computeIfAbsent(ip, this::newBucket);

        if (bucket.tryConsume(1) ) {
            chain.doFilter(request, response);
        } else {
            throw new RateLimitExceededException(Constants.MSG_EXCEPTION_RATE_LIMIT);
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
