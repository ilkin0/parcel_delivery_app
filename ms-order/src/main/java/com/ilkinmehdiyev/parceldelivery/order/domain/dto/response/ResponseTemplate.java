package com.ilkinmehdiyev.parceldelivery.order.domain.dto.response;

public record ResponseTemplate<T>(T data, StatusResponse status) {}
