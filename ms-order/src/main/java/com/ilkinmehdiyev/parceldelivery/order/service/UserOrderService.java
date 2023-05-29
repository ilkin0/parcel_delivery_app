package com.ilkinmehdiyev.parceldelivery.order.service;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.CreateOrderRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.CreateOrderResponse;

public interface UserOrderService {
  CreateOrderResponse createOrder(CreateOrderRequest orderRequest);
}
