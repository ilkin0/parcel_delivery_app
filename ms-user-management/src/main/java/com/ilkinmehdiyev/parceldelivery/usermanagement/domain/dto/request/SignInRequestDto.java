package com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(@NotBlank String username, @NotBlank String password) {}
