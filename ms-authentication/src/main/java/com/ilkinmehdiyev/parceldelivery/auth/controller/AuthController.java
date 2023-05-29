package com.ilkinmehdiyev.parceldelivery.auth.controller;

import com.ilkinmehdiyev.parceldelivery.auth.domain.dto.IntrospectResponse;
import com.ilkinmehdiyev.parceldelivery.auth.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
  private final UserInfoService userInfoService;

  @PostMapping("/introspect")
  public ResponseEntity<IntrospectResponse> introspect(
      @RequestHeader("Authorization") String authToken) {
    log.info("Starting to User Authorization...");
    IntrospectResponse response = userInfoService.fetchUserInfo(authToken);
    return ResponseEntity.ok(response);
  }
}
