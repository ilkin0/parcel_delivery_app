package com.ilkinmehdiyev.parceldelivery.order.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateOrderResponse(
    Integer userId, Integer courierId, String destination, OrderStatus orderStatus) {}
