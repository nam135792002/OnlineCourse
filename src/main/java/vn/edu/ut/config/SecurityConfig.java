package vn.edu.ut.config;

import vn.edu.ut.security.JwtAuthFilter;
import vn.edu.ut.security.JwtAuthenticationEntryPoint;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.addAllowedOrigin("*");
            corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
            corsConfig.addAllowedHeader("*");
            return corsConfig;
        }));
        http.authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/api/categories/create", "/api/categories/update/**", "/api/categories/delete/**",
                                        "/api/courses/*/chapters/**", "/api/contest/create", "/api/contest/delete/**", "/api/contest/switch-enabled",
                                        "/api/contest/get/**", "/api/contest/update/**", "/api/contest/ranking/reset/**",
                                        "/api/courses/create", "/api/courses/get/**", "/api/courses/update/**", "/api/courses/delete/**", "/api/courses/list-all",
                                        "/api/courses/switch-enabled", "/api/courses/switch-published", "/api/courses/switch-finished", "/api/lessons/**",
                                        "/api/feedback/get/**", "/api/feedback/list-all", "/api/feedback/delete/**", "/api/feedback/send-email",
                                        "/api/orders/list-all", "/api/orders/delete/**", "/api/quiz/delete/**", "/api/report/**", "/api/reviews/get-all").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/blog/get-all/user/**", "/api/orders/get-all/user/**", "/api/blog/update/**", "/api/blog/save", "/api/blog/delete/**", "/api/certificate/get/**",
                                        "/api/contest/join/**", "/api/learning/**", "/api/notes/**", "/api/orders/create", "/api/payment/**", "/api/qa/**",
                                        "/api/quiz/**", "/api/record/**", "/api/reviews/create", "/api/reviews/update/**", "/api/reviews/delete/**", "/api/reviews/check-reviewed/**",
                                        "/api/track-course/**", "/api/users/**").authenticated()
                                .requestMatchers("/api/auth/**", "/api/blog/**", "/api/categories/**", "/api/contest/**", "/api/courses/**", "/api/reviews/**", "/api/feedback/send",
                                        "/swagger-ui/**", "/v3/api-docs/**").permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oath2 -> oath2.successHandler(oAuth2LoginSuccessHandler))
        ;

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
