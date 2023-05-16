package com.example.todo2.auth.config;

import com.example.todo2.auth.filters.JwtFilter;
import com.example.todo2.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final CustomUserDetailsService userService;

    private final JwtFilter jwtFilter;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(CustomUserDetailsService userService, JwtFilter jwtFilter, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic().disable()
                .authorizeHttpRequests(
                        authorize -> authorize.requestMatchers("/api/v1/token", "/api/v1/auth", "/", "/api/v1/user", "/api-docs", "/api-docs/*", "/api-docs-ui.html", "/swagger-ui/*")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/todos").hasAnyAuthority("ADMIN")
                                .anyRequest()
                                .authenticated())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
