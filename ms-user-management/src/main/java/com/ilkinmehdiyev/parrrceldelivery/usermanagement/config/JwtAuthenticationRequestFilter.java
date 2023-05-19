package com.ilkinmehdiyev.parrrceldelivery.usermanagement.config;

import com.ilkinmehdiyev.parrrceldelivery.usermanagement.constant.HttpHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationRequestFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String jwtToken;

    if ( Objects.isNull(authHeader) || !authHeader.startsWith(HttpHeaders.BEARER)){
      filterChain.doFilter(request, response);
      return;
    }

    jwtToken = authHeader.substring(HttpHeaders.BEARER.length());

  }
}
