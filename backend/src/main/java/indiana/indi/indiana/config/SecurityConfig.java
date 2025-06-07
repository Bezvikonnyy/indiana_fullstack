package indiana.indi.indiana.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.HiddenHttpMethodFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // отключает "ROLE_" префикс
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/user/login",
                                "/user/registration",
                                "/home",
                                "/game/*",
                                "/css/**",
                                "/js/**")
                        .permitAll()
                        .requestMatchers("/user/edit_profile/**")
                        .hasAnyRole("Пользователь", "Автор", "Администратор")
                        .requestMatchers("/game/delete/**", "/game/edit/**")
                        .hasAnyRole("Автор", "Администратор")
                        .anyRequest().authenticated() // всё остальное требует входа
                )
                .formLogin(form -> form
                        .loginPage("/user/login")           // Стандартная страница логина
                        .loginProcessingUrl("/login")  // Стандартный путь для обработки POST-запроса
                        .defaultSuccessUrl("/home", true)   // После успешного входа перенаправление на главную
                        .failureUrl("/user/login?error=true") // В случае ошибки авторизации
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/user/login")  // Страница для логаута
                )
                .build();
    }
}
