package com.ilkinmehdiyev.parceldelivery.usermanagement.controller;

import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.response.UserInfoResponse;
import com.ilkinmehdiyev.parceldelivery.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  @GetMapping("/search")
  public ResponseEntity<UserInfoResponse> searchUser(@RequestParam(name = "email") String email) {
    log.info("Received request for finding user id and role for email {}", email);
    UserInfoResponse userInfoResponse = userService.searchUserEmail(email);
    log.info("Completed request for finding user id and role for email {}", email);
    return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
  }
}
