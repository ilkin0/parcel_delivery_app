package com.ilkinmehdiyev.parceldelivery.order.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OrderDestinationRequest(@NotBlank String newDestination) {}
