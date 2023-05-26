package com.ilkinmehdiyev.parceldelivery.gateway.security.filter;

import com.ilkinmehdiyev.parceldelivery.gateway.security.Credentials;
import com.ilkinmehdiyev.parceldelivery.gateway.security.CustomRouteValidator;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
// @AllArgsConstructor
// @NoArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter, Ordered {

  private final CustomRouteValidator routeValidator;

  @Value("${security.auth.url}")
  private String authServiceBase;

  @Value("${security.auth.introspect-api}")
  private String authServiceIntrospect;

  //  private final WebClient webClient;

  //  public JwtAuthenticationFilter(
  //      CustomRouteValidator routeValidator, WebClient.Builder webClientBuilder) {
  //    this.routeValidator = routeValidator;
  //    this.webClient = webClientBuilder.baseUrl(authServiceBase).build();
  //  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    final String token = getAuthHeader(request);

    if (routeValidator.isSecured.test(request)) {
      if (isAuthMissing(request) || !StringUtils.hasLength(token)) {
        return this.onError(
            exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
      }

      log.info("AUHT URI: {}", authServiceIntrospect);
      log.info("AUHT BASE: {}", authServiceBase);

      WebClient webClient =
          WebClient.builder()
              //          .mutate()
              .baseUrl(authServiceBase)
              .build();

      return webClient
          .post()
          .uri(authServiceIntrospect)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
          .retrieve()
          .bodyToMono(Credentials.class)
          .flatMap(
              credentials -> {
                log.info("Starting authentication");
                // Set the custom 'userId' header in the request
                ServerHttpRequest modifiedRequest =
                    exchange
                        .getRequest()
                        .mutate()
                        .header("userId", String.valueOf(credentials.userId()))
                        .header("role", String.valueOf(credentials.role()))
                        .build();

                // Route the modified request
                ServerWebExchange modifiedExchange =
                    exchange.mutate().request(modifiedRequest).build();
                return chain.filter(modifiedExchange);
              })
          .onErrorResume(
              ex ->
                  onError(
                      exchange,
                      "Failed to authenticate token: \n".concat(ex.getMessage()),
                      HttpStatus.UNAUTHORIZED));
    }

    //      processCredentials(exchange, token)
    //          .flatMap(
    //              credentials -> {
    //                if (Objects.nonNull(credentials)) {
    //                  populateRequestWithHeaders(exchange, credentials);
    //                  return chain.filter(exchange);
    //                } else {
    //                  return onError(
    //                      exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
    //                }
    //              });

    //      Credentials credentials = getCredentials1(token);
    //      if (Objects.nonNull(credentials)) {
    //        log.info("RES: {}", credentials);
    //        populateRequestWithHeaders(exchange, credentials);
    //      } else {
    //        return onError(exchange, "Authorization header is invalid",
    // HttpStatus.UNAUTHORIZED);
    //      }

    //    return onError(exchange, "Authorization header is missing or invalid",
    // HttpStatus.UNAUTHORIZED);
    return chain.filter(exchange);
  }
  //    return chain.filter(exchange);

  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
    //    ServerHttpResponse response = exchange.getResponse();
    //    response.setStatusCode(httpStatus);
    //    return response.setComplete();
    log.error("ERROR ON CALL: {}", err);
    exchange.getResponse().setStatusCode(httpStatus);
    exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, "text/plain");
    return exchange
        .getResponse()
        .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(err.getBytes())));
  }

  private String getAuthHeader(ServerHttpRequest request) {
    return request.getHeaders().getFirst("Authorization");
  }

  private boolean isAuthMissing(ServerHttpRequest request) {
    return !request.getHeaders().containsKey("Authorization");
  }

  private void populateRequestWithHeaders(ServerWebExchange exchange, Credentials credentials) {
    log.info("User authenticated: {}", credentials);

    ServerHttpRequest request = exchange.getRequest();
    ServerHttpRequest mutatedRequest =
        request
            .mutate()
            .header("userId", String.valueOf(credentials.userId()))
            .header("role", String.valueOf(credentials.role()))
            .build();

    exchange.mutate().request(mutatedRequest).build();
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

  private Mono<Credentials> getCredentials(String token) {
    WebClient webClient =
        WebClient.builder()
            .baseUrl(authServiceBase)
            .defaultHeader(HttpHeaders.AUTHORIZATION, token)
            .build();

    return webClient.post().retrieve().bodyToMono(Credentials.class);
  }

  private Credentials getCredentials1(String token) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", token);

    HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);
    ResponseEntity<Credentials> exchange =
        restTemplate.exchange(authServiceBase, HttpMethod.POST, requestEntity, Credentials.class);

    if (exchange.getStatusCode().equals(HttpStatus.OK)) {
      return exchange.getBody();
    }

    return null;
  }

  private Mono<Credentials> processCredentials(ServerWebExchange exchange, String token) {
    return getCredentials(token)
        .doOnSuccess(
            credentials -> {
              if (Objects.nonNull(credentials)) {
                populateRequestWithHeaders(exchange, credentials);
              }
            })
        .onErrorResume(
            error -> {
              onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
              return Mono.empty();
            });
  }

  @Override
  public int getOrder() {
    return -1;
  }
}
