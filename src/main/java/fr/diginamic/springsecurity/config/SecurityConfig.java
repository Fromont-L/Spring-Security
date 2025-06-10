package fr.diginamic.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService users() {
        UserDetails user1 = User.withDefaultPasswordEncoder()
                .username("jose")
                .password("1111")
                .roles("USER")
                .build();
        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("jaaj")
                .password("4444")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Activation du CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/hello/public", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // URL de la page de login personnalisée
                        .defaultSuccessUrl("/hello/private", true)
                        .failureUrl("/hello/public") // si echec
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/hello/public")
                        .permitAll()
                );

        return http.build();
    }
}
