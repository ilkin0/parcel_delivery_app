package com.ilkinmehdiyev.parceldelivery.order.service.impls;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.CreateOrderRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.OrderDestinationRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.CreateOrderResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.GetAllOrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderCancelResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderDestinationResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus;
import com.ilkinmehdiyev.parceldelivery.order.domain.enums.UserRole;
import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;
import com.ilkinmehdiyev.parceldelivery.order.domain.repository.OrderRepository;
import com.ilkinmehdiyev.parceldelivery.order.service.OrderService;
import com.ilkinmehdiyev.parceldelivery.order.utility.EntityUtility;
import com.ilkinmehdiyev.parceldelivery.order.utility.SessionUser;
import com.ilkinmehdiyev.parceldelivery.order.utility.ThreadLocalStorage;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
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

  @Override
  public Order getOrderById(Integer orderId) {
    return orderRepository
        .findById(orderId)
        .orElseThrow(
            () -> {
              log.error("Cannot find Order with {} orderId.", orderId);
              return new IllegalArgumentException("Cannot find Order with orderId.");
            });
  }

  @Override
  public OrderDetailsResponse getOrderDetailsById(Integer orderId) {
    validateUserForOrderDetails();

    log.info("Fetching order details by {} orderId.", orderId);

    Order orderById = getOrderById(orderId);
    log.info("Found order: {}", orderById);

    return EntityUtility.getOrderDetailsResponse(orderById);
  }

  @Override
  public OrderDestinationResponse changeDestination(
      Integer orderId, OrderDestinationRequest request) {
    log.info("Starting to Change Orders' destination...");

    Order orderById = getOrderById(orderId);
    log.info("Found Order: {}", orderById);

    validateOrderCreateStatus(orderById);

    orderById.setDestination(request.newDestination());
    Order savedOrder = orderRepository.save(orderById);
    return new OrderDestinationResponse(savedOrder.getDestination());
  }

  private static void validateOrderCreateStatus(Order orderById) {
    if (!OrderStatus.CREATED.equals(orderById.getOrderStatus())) {
      log.error("Order in {} status, destination cannot be changed.", orderById.getOrderStatus());
      throw new IllegalArgumentException();
    }
  }

  @Override
  @Transactional
  public OrderCancelResponse deleteById(Integer orderId) {
    log.info("Starting to cancel Order with {} orderId.", orderId);
    validateUserForCancelOrder();

    Integer updateCount = orderRepository.deleteOrderById(orderId);
    if (updateCount <= 0) {
      log.error("Order not found or already canceled.");
    }

    return new OrderCancelResponse(updateCount);
  }

  @Override
  public GetAllOrderDetailsResponse getAllOrders() {
    Integer userId = ThreadLocalStorage.getSessionUser().getUserId();
    log.info("Fetching all Parcel Order details for user {}", userId);

    List<Order> allByUserId = orderRepository.getAllByUserId(userId);
    log.info("Found {} order for user.", allByUserId);

    List<OrderDetailsResponse> responseList =
        allByUserId.stream()
            .map(EntityUtility::getOrderDetailsResponse)
            .collect(Collectors.toList());

    return new GetAllOrderDetailsResponse(responseList);
  }

  private void validateUserForCancelOrder() {
    String role = ThreadLocalStorage.getSessionUser().getRole();
    if (!UserRole.USER.toString().equalsIgnoreCase(role)) {
      log.error("Only user can cancel Order!");
      throw new IllegalArgumentException();
    }
  }

  private void validateUserForOrderDetails() {
    String role = ThreadLocalStorage.getSessionUser().getRole();
    Integer userId = ThreadLocalStorage.getSessionUser().getUserId();
    log.info("Validating user with {} orderId.", userId);

    if (!(UserRole.USER.toString().equalsIgnoreCase(role)
        || UserRole.ADMIN.toString().equalsIgnoreCase(role))) {
      log.error("Only User/Admin can get details of Order");
      throw new IllegalStateException("Only User/Admin can get details of Order");
    }
  }
}
