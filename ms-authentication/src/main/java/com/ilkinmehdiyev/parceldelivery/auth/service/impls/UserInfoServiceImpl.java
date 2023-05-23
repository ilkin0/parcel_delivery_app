package com.ilkinmehdiyev.parceldelivery.auth.service.impls;

import com.ilkinmehdiyev.parceldelivery.auth.domain.dto.IntrospectResponse;
import com.ilkinmehdiyev.parceldelivery.auth.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoServiceImpl implements UserInfoService {

  @Value("${services.user.base")
  private String userServiceApiBaseUrl;

  @Value("${services.user.searchUserApi}")
  private String searchUserApiUrl;

  private final JwtService jwtService;

  @Override
  public IntrospectResponse fetchUserInfo(String authToken) {
    log.info("Authentication for {} auth token started...", authToken);
    String email = getEmailFromToken(authToken);

    log.info("Request to fetch userId from user-service received for {}", email);

    WebClient webClient = WebClient.builder().baseUrl(userServiceApiBaseUrl).build();

    Mono<IntrospectResponse> introspectResponseMono =
        webClient
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder.path(searchUserApiUrl).queryParam("email", "{email}").build(email))
            .retrieve()
            .bodyToMono(IntrospectResponse.class);

    log.info("Request to fetch userId from user-service completed for {}", email);
    return introspectResponseMono.block();
  }

  private String getEmailFromToken(String token) {
    return jwtService.parseToken(token).getSubject();
  }
}
