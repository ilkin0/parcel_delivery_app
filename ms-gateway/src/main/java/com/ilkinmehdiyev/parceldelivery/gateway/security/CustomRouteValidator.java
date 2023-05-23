package com.ilkinmehdiyev.parceldelivery.gateway.security;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomRouteValidator {

  public static final List<String> ALLOWED_URLS = List.of("/auth/sign-up", "/auth/sign-in");

  public Predicate<ServerHttpRequest> isSecured =
      httpRequest ->
          ALLOWED_URLS.stream().noneMatch(uri -> httpRequest.getURI().getPath().contains(uri));
}
