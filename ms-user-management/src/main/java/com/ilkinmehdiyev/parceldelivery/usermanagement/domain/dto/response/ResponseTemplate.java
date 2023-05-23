package com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.response;

public record ResponseTemplate<T>(T data, StatusResponse status) {}
