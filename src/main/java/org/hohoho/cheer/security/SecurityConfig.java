package org.hohoho.cheer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
                        .ignoringRequestMatchers("/?v-r=uidl", "/error")) // Disable CSRF for specific endpoints
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/VAADIN/**", "/vaadinServlet/**", "/frontend/**", "/images/**").permitAll()
                        .requestMatchers("/login", "/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login").permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}