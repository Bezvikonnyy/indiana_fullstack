package indiana.indi.indiana.config;

import indiana.indi.indiana.service.user.customUser.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    CorsConfiguration config = new CorsConfiguration();

                    config.setAllowedOrigins(List.of("http://localhost:5173"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    config.setExposedHeaders(List.of("Authorization"));
                    config.setAllowCredentials(true);

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", config);

                    cors.configurationSource(source);
                })
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/user/login",
                                "/api/user/registration",
                                "/api/home/**",
                                "/api/news/allNews",
                                "/api/news/",
                                "/api/news/*",
                                "/api/game",
                                "/api/game/*",
                                "/css/**",
                                "/js/**",
                                "/uploads/**",
                                "/api/cart/callback/*")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/comment/**").permitAll()
                        .requestMatchers("/api/user/edit_profile",
                                "/api/user/delete_profile",
                                "/api/user/favorite/toggle/*",
                                "/api/comment/create_comment",
                                "/api/comment/edit_comment/**",
                                "/api/comment/delete_comment/**",
                                "/api/cart/**"
                                )
                        .hasAnyAuthority("ROLE_USER", "ROLE_AUTHOR", "ROLE_ADMIN")
                        .requestMatchers("/api/categories/**")
                        .hasAnyAuthority("ROLE_AUTHOR", "ROLE_ADMIN")
                        .requestMatchers("/api/admin/**")
                        .hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(
                                "/api/game/delete/*",
                                "/api/game/edit/**",
                                "/api/game/new_game",
                                "/api/news/newNews",
                                "/api/news/edit/**",
                                "/api/news/delete/**"
                        )
                        .hasAnyAuthority("ROLE_AUTHOR", "ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
