package com.ilkinmehdiyev.parceldelivery.order.utility;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EntityUtility {
  public static OrderDetailsResponse getOrderDetailsResponse(Order orderById) {
    return new OrderDetailsResponse(
        orderById.getCourierId(),
        orderById.getDestination(),
        orderById.getOrderStatus(),
        orderById.getDeliveryDate());
  }
}
