package pe.edu.upeu.bibliotecabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class BibliotecaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaBackendApplication.class, args);
    }

    // Función para que Lambda ejecute el código Spring Boot
    // La infraestructura de Spring Cloud Function se encarga de dirigir
    // las peticiones al controlador @RestController correcto.
    @Bean
    public Function<String, String> echo() {
        return value -> value;
        // Esta función es mínima; la magia ocurre cuando API Gateway
        // invoca a Spring Cloud Function, que arranca el contexto
        // y dirige la petición HTTP al controlador.
    }
}
