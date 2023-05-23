package com.ilkinmehdiyev.parceldelivery.order.service;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.CreateOrderRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.CreateOrderResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;

public interface OrderService {
  CreateOrderResponse createOrder(CreateOrderRequest orderRequest);

  Order saveOrder(Order order);
}
