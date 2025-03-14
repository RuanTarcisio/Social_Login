package io.github.ruantarcisio.backend.auth;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import io.github.ruantarcisio.backend.config.ApplicationProperties;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

  private final ApplicationProperties applicationProperties;
  private final UserDetailsService userDetailsService;
  private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(customizer -> {
      customizer
              .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
              .requestMatchers(HttpMethod.GET, "/api/users/verify-email").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/users/forgot-password").permitAll()
              .requestMatchers(HttpMethod.PATCH, "/api/users/reset-password").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
              .requestMatchers(HttpMethod.GET, "/api/auth/teste").permitAll()
              .requestMatchers("/oauth2/authorization/google").permitAll()

              .anyRequest().authenticated();
    });

    http.oauth2Login(customizer -> {
      customizer.successHandler(oauth2LoginSuccessHandler);
      customizer.failureHandler((request, response, exception) -> {
        log.error("OAuth2 Authentication failed", exception);
        response.sendRedirect("/login?error=true");});
    });
    
    http.exceptionHandling(customizer -> {
      customizer.authenticationEntryPoint(
              (request, response, authException) -> {
                response.sendError(401, "Unauthorized");
              });
    });

    http.addFilterBefore(new UsernamePasswordAuthenticationFilter(), LogoutFilter.class);
    http.userDetailsService(userDetailsService);

    http.csrf(csrf -> {
      csrf.disable(); // TODO: We will implement CSRF protection later
    });

    http.cors(customizer -> {
      customizer.configurationSource(corsConfigurationSource());
    });

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private CorsConfigurationSource corsConfigurationSource() {
    return new CorsConfigurationSource() {
      @Override
      public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        return config;
      }
    };
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(daoAuthenticationProvider);
  }
}