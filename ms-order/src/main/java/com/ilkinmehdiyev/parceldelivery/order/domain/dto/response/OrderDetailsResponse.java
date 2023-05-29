package com.ilkinmehdiyev.parceldelivery.order.domain.dto.response;

import com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus;
import java.time.LocalDateTime;

public record OrderDetailsResponse(
    Integer courierId, String destination, OrderStatus orderStatus, LocalDateTime deliveryDate) {}
