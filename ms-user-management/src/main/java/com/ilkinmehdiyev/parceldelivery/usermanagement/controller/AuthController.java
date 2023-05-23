package com.ilkinmehdiyev.parceldelivery.usermanagement.controller;

import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.RefreshTokenDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.TokenRefreshResponseDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.request.SignInRequestDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.request.SignUpRequestDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.response.ResponseTemplate;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.response.SignUpResponseDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.service.AuthService;
import com.ilkinmehdiyev.parceldelivery.usermanagement.service.UserService;
import com.ilkinmehdiyev.parceldelivery.usermanagement.utility.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/auth")
public class AuthController {
  private final UserService userService;
  private final AuthService authService;

  @PostMapping("/sign-up")
  public ResponseEntity<ResponseTemplate<SignUpResponseDto>> signUpUser(
      @RequestBody @Validated SignUpRequestDto requestDto) {
    log.info("Starting user sign-up");
    SignUpResponseDto responseDto = userService.registerUser(requestDto);

    return new ResponseEntity<>(
        ResponseUtility.generateResponse(
            responseDto, HttpStatus.OK.value(), "User created successfully"),
        HttpStatus.OK);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<ResponseTemplate<TokenRefreshResponseDto>> signInUser(
      @RequestBody @Validated SignInRequestDto requestDto) {
    log.info("Starting user sign-in");
    TokenRefreshResponseDto responseDto = authService.singInUser(requestDto);

    return new ResponseEntity<>(
        ResponseUtility.generateResponse(responseDto, HttpStatus.OK.value(), "Sign in success"),
        HttpStatus.OK);
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<TokenRefreshResponseDto> accessToken(
      @RequestBody RefreshTokenDto refreshTokenDto) {
    TokenRefreshResponseDto responseDto = authService.refreshAccessToken(refreshTokenDto);

    return ResponseEntity.ok(responseDto);
  }
}
