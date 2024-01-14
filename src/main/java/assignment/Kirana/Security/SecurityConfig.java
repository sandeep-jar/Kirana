package assignment.Kirana.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** Security configuration class for securing the Kirana application. */
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the security filter chain for HTTP security settings.
     *
     * @param http HttpSecurity instance for configuring security settings.
     * @return SecurityFilterChain configured with authentication and authorization rules.
     * @throws Exception If an exception occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests()
                .requestMatchers("/users")
                .permitAll()
                .requestMatchers("/user/login/**")
                .permitAll()
                .requestMatchers("/report/**")
                .hasAuthority("ADMIN")
                .requestMatchers("/transactions/**")
                .hasAnyAuthority("ADMIN", "USER")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
