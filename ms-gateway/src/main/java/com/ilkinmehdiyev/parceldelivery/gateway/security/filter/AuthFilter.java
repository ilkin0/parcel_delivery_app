//package com.ilkinmehdiyev.parceldelivery.gateway.security.filter;
//
//import com.ilkinmehdiyev.parceldelivery.gateway.security.Credentials;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Component
//public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
//
//  private final WebClient.Builder webClientBuilder;
//
//  @Value("${security.auth-service-api}")
//  private String authServiceApi;
//
//  public AuthFilter(WebClient.Builder webClientBuilder) {
//    super(Config.class);
//    this.webClientBuilder = webClientBuilder;
//  }
//
//  @Override
//  public GatewayFilter apply(Config config) {
//    return (exchange, chain) -> {
//      if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//        throw new RuntimeException("Missing authorization information");
//      }
//
//      if (isAuthMissing(exchange.getRequest())) {
//        throw new RuntimeException("Incorrect authorization structure");
//      }
//
//      String authHeader = getAuthHeader(exchange.getRequest());
//
//      return webClientBuilder
//          .build()
//          .post()
//          .uri(authServiceApi)
//          .header(HttpHeaders.AUTHORIZATION, authHeader)
//          .retrieve()
//          .bodyToMono(Credentials.class)
//          .map(
//              credentials -> {
//                exchange
//                    .getRequest()
//                    .mutate()
//                    .header("X-auth-user-id", String.valueOf(credentials.userId()))
//                    .header("X-auth-role", String.valueOf(credentials.role()));
//                return exchange;
//              })
//          .flatMap(chain::filter);
//    };
//  }
//
//  private String getAuthHeader(ServerHttpRequest request) {
//    return request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//  }
//
//  private boolean isAuthMissing(ServerHttpRequest request) {
//    return !request.getHeaders().containsKey("Authorization");
//  }
//
//  public static class Config {}
//}
