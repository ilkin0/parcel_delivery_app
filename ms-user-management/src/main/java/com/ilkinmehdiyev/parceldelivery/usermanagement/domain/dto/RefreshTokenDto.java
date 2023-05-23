package com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record RefreshTokenDto(@NotBlank String token, LocalDateTime expiryDate) {}
