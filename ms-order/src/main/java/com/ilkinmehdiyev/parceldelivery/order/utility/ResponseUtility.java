package com.ilkinmehdiyev.parceldelivery.order.utility;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ResponseTemplate;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.StatusResponse;
import org.springframework.http.HttpStatus;

public class ResponseUtility {
  public static <T> ResponseTemplate<T> generateResponse(
      T responseData, Integer statusCode, String message) {
    return new ResponseTemplate(responseData, new StatusResponse(statusCode, message));
  }

  public static <T> ResponseTemplate<T> generateResponse(String message) {
    return new ResponseTemplate(null, new StatusResponse(HttpStatus.OK.value(), message));
  }
}
