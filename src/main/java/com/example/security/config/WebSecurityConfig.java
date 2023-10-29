package com.example.security.config;

import com.example.security.config.SecurityDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers(HttpMethod.POST, "/", "/login").permitAll()
//                        .requestMatchers("/admins").hasRole("ADMINS")
//                        .requestMatchers("/users").hasAnyRole("ADMINS", "USERS")
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
//
//        return http.build();
//    }

    @Autowired
    private SecurityDatabaseService securityService;
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admins").hasRole("ADMINS")
                        .requestMatchers("/users").hasAnyRole("ADMINS", "USERS")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
                //.formLogin(withDefaults());

        return http.build();
    }

    /* We can comment this because now we are using JPA to retrieve user data from DB
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}user123")
                .roles("USERS")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}master123")
                .roles("USERS", "ADMINS")
                .build();
        //return new InMemoryUserDetailsManager(user);
        return new InMemoryUserDetailsManager(user, admin);
    }
    */
}