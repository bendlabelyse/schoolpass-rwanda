package rw.schoolpass.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    InMemoryUserDetailsManager users(
            PasswordEncoder encoder,
            @Value("${schoolpass.admin.username}") String adminUser,
            @Value("${schoolpass.admin.password}") String adminPass,
            @Value("${schoolpass.finance.username}") String financeUser,
            @Value("${schoolpass.finance.password}") String financePass) {

        var admin = User.withUsername(adminUser)
                .password(encoder.encode(adminPass))
                .roles("ADMIN", "FINANCE")
                .build();

        var finance = User.withUsername(financeUser)
                .password(encoder.encode(financePass))
                .roles("FINANCE")
                .build();

        return new InMemoryUserDetailsManager(admin, finance);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/css/**",
                        "/js/**",
                        "/uploads/**",
                        "/login"
                ).permitAll()

                .requestMatchers(
                        "/finance/**",
                        "/api/finance/**"
                ).hasAnyRole("ADMIN", "FINANCE")

                .requestMatchers(
                        "/admin/**",
                        "/students/new",
                        "/students/*/edit",
                        "/students/*/replace-card",
                        "/students/import"
                ).hasRole("ADMIN")

                .anyRequest().authenticated()
        )

        .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
        )

        .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        )

        .sessionManagement(session -> session
                .maximumSessions(3)
        );

        return http.build();
    }
}