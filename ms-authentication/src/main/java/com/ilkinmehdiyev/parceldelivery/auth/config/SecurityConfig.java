package com.ilkinmehdiyev.parceldelivery.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  String[] ALLOWED_URLS = new String[] {"/auth/introspect", "/auth/token", "/auth/validate"};

  //  @Bean
  //  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
  //    return httpSecurity
  //        .authorizeHttpRequests(
  //            requestMatcherRegistry ->
  //                requestMatcherRegistry.requestMatchers(ALLOWED_URLS).permitAll()
  //            //                    .anyRequest()
  //            //                    .authenticated()
  //            )
  //        .csrf(AbstractHttpConfigurer::disable)
  //        .httpBasic(AbstractHttpConfigurer::disable)
  //        .formLogin(AbstractHttpConfigurer::disable)
  //        .build();
  //  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            requestMatcherRegistry ->
                requestMatcherRegistry.anyRequest().permitAll())
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
