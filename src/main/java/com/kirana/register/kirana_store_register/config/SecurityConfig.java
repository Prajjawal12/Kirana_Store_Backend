package com.kirana.register.kirana_store_register.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.kirana.register.kirana_store_register.utils.JwtUtil;

/**
 * Security configuration for the application, including HTTP security settings
 * and password encoding.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private CustomAccessDeniedHandlers customAccessDeniedHandlers;

  /**
   * Bean for encoding passwords using BCrypt.
   *
   * @return the PasswordEncoder instance
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the security filter chain for the application.
   *
   * @param http the HttpSecurity instance
   * @return the SecurityFilterChain
   * @throws Exception if an error occurs while configuring security
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/reports/**").hasAnyRole("READ_WRITE", "READ_ONLY")
            .requestMatchers("/api/transactions/**").hasRole("READ_WRITE")
            .anyRequest().authenticated())
        .exceptionHandling(exceptions -> exceptions.accessDeniedHandler(customAccessDeniedHandlers))
        .addFilterBefore(new JwtRequestFilter(jwtUtil), BasicAuthenticationFilter.class)
        .csrf(csrf -> csrf.disable());

    return http.build();
  }
}
