package com.ilkinmehdiyev.parceldelivery.order.domain.dto.response;

import com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus;

public record ChangeOrderStatusResponse(Integer orderId, OrderStatus orderStatus) {}
