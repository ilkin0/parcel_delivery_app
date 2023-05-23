package com.ilkinmehdiyev.parceldelivery.auth.service;

import com.ilkinmehdiyev.parceldelivery.auth.domain.dto.IntrospectResponse;

public interface UserInfoService {
  IntrospectResponse fetchUserInfo(String authToken);
}
