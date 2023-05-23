package com.ilkinmehdiyev.parceldelivery.order.domain.dto.response;

import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.response.StatusResponse;

public record ResponseTemplate<T>(T data, StatusResponse status) {}
