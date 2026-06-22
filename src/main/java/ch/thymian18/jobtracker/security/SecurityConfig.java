package ch.thymian18.jobtracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, UserDetailsService userDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())   //csrf-protection not needed for JWT-based application; it would be for sessions/cookies-based application
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   //  sagt Spring Security explizit: "Erstelle keine Server-Side-Sessions." Jeder Request muss sich selbst über das JWT authentifizieren, der Server merkt sich zwischen Requests nichts.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )   // Alles unter /auth/... (Login, Registrierung) ist öffentlich zugänglich — alles andere braucht eine gültige Authentifizierung
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // unser Filter wird vor anderen in der Kette ausgeführt

        return http.build();
    }
}
