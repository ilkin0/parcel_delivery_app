package com.ilkinmehdiyev.parceldelivery.order.service.impls;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.CreateOrderRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.CreateOrderResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus;
import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;
import com.ilkinmehdiyev.parceldelivery.order.domain.repository.OrderRepository;
import com.ilkinmehdiyev.parceldelivery.order.mapper.OrderMapper;
import com.ilkinmehdiyev.parceldelivery.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;

  @Override
  public CreateOrderResponse createOrder(CreateOrderRequest orderRequest) {
    log.info("Starting to create a new parcel delivery order...");
    Order order = OrderMapper.INSTANCE.toOrder(orderRequest);
    order.setOrderStatus(OrderStatus.CREATED);
    Order savedOrder = saveOrder(order);

    log.error("Created order.");
    return OrderMapper.INSTANCE.toCreateOrderResponse(savedOrder);
  }

  @Override
  public Order saveOrder(Order order) {
    try {
      Order saved = orderRepository.save(order);
      log.info("Order saved: {}", saved);
      return saved;
    } catch (Exception e) {
      log.error("Cannot save {}", order);
      throw new IllegalArgumentException(e);
    }
  }
}
