//package com.ilkinmehdiyev.parceldelivery.gateway.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Slf4j
//// @RequiredArgsConstructor
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//  @Bean
//  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
//    log.info("HttpSecurity configuration started");
//
//    //    return httpSecurity
//    //            .authorizeHttpRequests(
//    //                    requestMatcherRegistry ->
//    //                            requestMatcherRegistry
//    //
//    // .requestMatchers(String.valueOf(CustomRouteValidator.ALLOWED_URLS))
//    //                                    .permitAll()
//    //                                    .anyRequest()
//    //                                    .authenticated())
//    //            .headers(
//    //                    headersConfigurer ->
//    //
//    // headersConfigurer.cacheControl(HeadersConfigurer.CacheControlConfig::disable))
//    //            .httpBasic(AbstractHttpConfigurer::disable)
//    //            .sessionManagement(
//    //                    sessionManagementConfigurer ->
//    //
//    // sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//    //            .csrf(AbstractHttpConfigurer::disable)
//    //            .build();
//    ServerHttpSecurity httpSecurity =
//        http.authorizeExchange(
//            authorizeExchangeSpec -> authorizeExchangeSpec.anyExchange().permitAll());
//
//    httpSecurity
//        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//        .formLogin(ServerHttpSecurity.FormLoginSpec::disable);
//
//    //        .headers(headerSpec ->
//    // headerSpec.cache(ServerHttpSecurity.HeaderSpec.CacheSpec::disable))
//    //        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//    //        .csrf(ServerHttpSecurity.CsrfSpec::disable)
//    //        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
//    return httpSecurity.build();
//  }
//}
