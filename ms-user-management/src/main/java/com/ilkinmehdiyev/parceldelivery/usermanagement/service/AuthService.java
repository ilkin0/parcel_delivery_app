package com.ilkinmehdiyev.parceldelivery.usermanagement.service;

import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.RefreshTokenDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.TokenRefreshResponseDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.request.SignInRequestDto;

public interface AuthService {
    TokenRefreshResponseDto singInUser(SignInRequestDto requestDto);
    TokenRefreshResponseDto refreshAccessToken(RefreshTokenDto refreshTokenDto);
}
