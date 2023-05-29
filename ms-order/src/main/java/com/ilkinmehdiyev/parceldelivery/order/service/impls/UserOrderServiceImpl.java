package com.ilkinmehdiyev.parceldelivery.order.service.impls;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.CreateOrderRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.CreateOrderResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus;
import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;
import com.ilkinmehdiyev.parceldelivery.order.service.UserOrderService;
import com.ilkinmehdiyev.parceldelivery.order.utility.SessionUser;
import com.ilkinmehdiyev.parceldelivery.order.utility.ThreadLocalStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOrderServiceImpl implements OrderServiceImpl, UserOrderService {
  @Override
  public CreateOrderResponse createOrder(CreateOrderRequest orderRequest) {
    log.info("Starting to create a new parcel delivery order...");

    SessionUser sessionUser = ThreadLocalStorage.getSessionUser();
    log.info("Current Session User: {}", sessionUser);

    Order order =
        Order.builder()
            .userId(orderRequest.userId())
            .destination(orderRequest.destination())
            .orderStatus(OrderStatus.CREATED)
            .build();

    Order savedOrder = saveOrder(order);
    log.info("Order created: {}", savedOrder);

    return new CreateOrderResponse(
        savedOrder.getUserId(),
        savedOrder.getCourierId(),
        savedOrder.getDestination(),
        savedOrder.getOrderStatus());
  }
}
