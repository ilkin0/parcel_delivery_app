package com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequestDto(
    @NotBlank String name,
    @NotBlank String username,
    @NotBlank String password,
    @NotBlank String role) {}
