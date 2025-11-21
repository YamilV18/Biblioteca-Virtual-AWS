package pe.edu.upeu.bibliotecabackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Define la clave dentro del token JWT donde Cognito almacena los grupos
    private static final String COGNITO_GROUPS_CLAIM = "cognito:groups";

    // Prefijo que Spring Security espera para los roles (ej. ROLE_ADMIN)
    private static final String ROLE_PREFIX = "ROLE_";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita CSRF (funcional)

                .authorizeHttpRequests(auth -> auth
                        // Permite lectura pública (no requiere token)
                        .requestMatchers("/api/libros", "/api/categorias", "/api/libros/*", "/api/categorias/*").permitAll()

                        // Las peticiones POST/PUT/DELETE requieren rol de ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/libros", "/api/categorias").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/libros/**", "/api/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/libros/**", "/api/categorias/**").hasRole("ADMIN")

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )

                // 2. Configuración del Resource Server con el conversor de roles
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    /**
     * Define el conversor de JWT que mapea los grupos de Cognito a roles de Spring Security.
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter groupsConverter = new JwtGrantedAuthoritiesConverter();
        groupsConverter.setAuthoritiesClaimName(COGNITO_GROUPS_CLAIM);
        groupsConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = groupsConverter.convert(jwt);

            Collection<GrantedAuthority> roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(groupName -> new SimpleGrantedAuthority(ROLE_PREFIX + groupName))
                    .collect(Collectors.toList());

            return roles;
        });

        return converter;
    }
}