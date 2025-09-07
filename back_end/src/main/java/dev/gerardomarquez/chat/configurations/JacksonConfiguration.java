package dev.gerardomarquez.chat.configurations;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfiguration {

    /*
     * Metodo que crea un ObjectMapper para convertir objetos a String de tipo json con la
     * configuracion para poder leer los tipos LocalDataTime
     * @return objectMapper que se puede inyectar para la conversion a string json
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer objectMapperCustom() {
         return builder -> builder.modules(new JavaTimeModule() );
    }
}
