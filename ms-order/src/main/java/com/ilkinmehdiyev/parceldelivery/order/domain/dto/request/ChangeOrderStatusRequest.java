package com.ilkinmehdiyev.parceldelivery.order.domain.dto.request;

import com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus;

public record ChangeOrderStatusRequest(OrderStatus orderStatus) {}
