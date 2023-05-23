package com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto;

public record TokenRefreshResponseDto(
    JwtAccessTokenDto accessToken, RefreshTokenDto refreshToken) {}
