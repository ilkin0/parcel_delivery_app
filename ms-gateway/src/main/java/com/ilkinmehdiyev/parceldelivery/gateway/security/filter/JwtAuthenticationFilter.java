package com.ilkinmehdiyev.parceldelivery.gateway.security.filter;

import com.ilkinmehdiyev.parceldelivery.gateway.security.Credentials;
import com.ilkinmehdiyev.parceldelivery.gateway.security.CustomRouteValidator;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter {

  private final CustomRouteValidator routeValidator;

  @Value("${security.auth-service-api}")
  private String authServiceApi;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();

    if (routeValidator.isSecured.test(request)) {
      if (this.isAuthMissing(request)) {
        return this.onError(
            exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
      }
      final String token = this.getAuthHeader(request);
      Credentials credentials = getCredentials(token);
      if (Objects.nonNull(credentials)) {
        this.populateRequestWithHeaders(exchange, credentials);
        //        this.populateSecurityContext(credentials, token);
      } else {
        return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
      }
    }
    return chain.filter(exchange);
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);
    return response.setComplete();
  }

  private String getAuthHeader(ServerHttpRequest request) {
    return request.getHeaders().getOrEmpty("Authorization").get(0);
  }

  private boolean isAuthMissing(ServerHttpRequest request) {
    return !request.getHeaders().containsKey("Authorization");
  }

  private void populateRequestWithHeaders(ServerWebExchange exchange, Credentials credentials) {
    exchange
        .getRequest()
        .mutate()
        .header("userId", String.valueOf(credentials.userId()))
        .header("role", String.valueOf(credentials.role()))
        .build();
  }

  //  private void populateSecurityContext(Credentials credentials, String token) {
  //    List<GrantedAuthority> roles = new ArrayList<>();
  //    roles.add(new SimpleGrantedAuthority("ROLE_" + credentials.role()));
  //
  //    UsernamePasswordAuthenticationToken authentication =
  //        new UsernamePasswordAuthenticationToken(token, null, roles);
  //    log.info("Token authenticated");
  //    SecurityContextHolder.getContext().setAuthentication(authentication);
  //  }

  private Credentials getCredentials(String token) {
    WebClient webClient =
        WebClient.builder()
            .baseUrl(authServiceApi)
            .defaultHeader(HttpHeaders.AUTHORIZATION, token)
            .build();
    Mono<Credentials> credentials = webClient.post().retrieve().bodyToMono(Credentials.class);
    return credentials.block();
  }
}
