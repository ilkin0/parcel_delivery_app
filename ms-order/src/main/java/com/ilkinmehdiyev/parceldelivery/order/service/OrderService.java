package com.ilkinmehdiyev.parceldelivery.order.service;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.CreateOrderRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.OrderDestinationRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.CreateOrderResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.GetAllOrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderCancelResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderDestinationResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;

public interface OrderService {
  CreateOrderResponse createOrder(CreateOrderRequest orderRequest);

  Order saveOrder(Order order);

  OrderDetailsResponse getOrderDetailsById(Integer orderId);

  Order getOrderById(Integer orderId);

  OrderDestinationResponse changeDestination(Integer orderId, OrderDestinationRequest request);

  OrderCancelResponse deleteById(Integer orderId);

  GetAllOrderDetailsResponse getAllOrders();
}
