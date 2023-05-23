package com.ilkinmehdiyev.parceldelivery.usermanagement.service;

import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.request.SignUpRequestDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.response.SignUpResponseDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.response.UserInfoResponse;

public interface UserService {
  SignUpResponseDto registerUser(SignUpRequestDto requestDto);

  UserInfoResponse searchUserEmail(String email);
}
