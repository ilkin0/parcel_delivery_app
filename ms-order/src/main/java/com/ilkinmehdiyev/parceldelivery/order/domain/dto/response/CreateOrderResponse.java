package com.ilkinmehdiyev.parceldelivery.order.domain.dto.response;

import com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus;

public record CreateOrderResponse(
    Integer userId, Integer courierId, String destination, OrderStatus orderStatus) {}
