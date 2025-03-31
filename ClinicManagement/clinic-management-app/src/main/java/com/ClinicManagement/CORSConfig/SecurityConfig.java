package com.ClinicManagement.CORSConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa CSRF para APIs REST
                .cors(withDefaults()) // Habilita CORS globalmente
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API sem sessão
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permite preflight requests sem autenticação
                        .requestMatchers("/patients/**").authenticated() // Protege os endpoints
                        .anyRequest().permitAll() // Outras requisições são públicas
                )
                .httpBasic(withDefaults()); // Habilita autenticação HTTP Basic

        return http.build();
    }

}
