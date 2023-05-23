package com.ilkinmehdiyev.parceldelivery.auth.controller;

import com.ilkinmehdiyev.parceldelivery.auth.domain.dto.IntrospectResponse;
import com.ilkinmehdiyev.parceldelivery.auth.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
  private final UserInfoService userInfoService;

  @PostMapping("/v1/introspect")
  public ResponseEntity<IntrospectResponse> introspect(
      @RequestHeader("Authorization") String authToken) {
    log.info("Starting to User Authorization...");
    //    IntrospectResponse response = userInfoService.fetchUserInfo(authToken);
    //    return ResponseEntity.ok(response);
    return ResponseEntity.ok(new IntrospectResponse("101", "USER"));
  }
}
