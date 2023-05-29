package com.ilkinmehdiyev.parceldelivery.order.domain.dto.response;

import java.util.List;

public record GetAllOrderDetailsResponse(List<OrderDetailsResponse> details) {}
