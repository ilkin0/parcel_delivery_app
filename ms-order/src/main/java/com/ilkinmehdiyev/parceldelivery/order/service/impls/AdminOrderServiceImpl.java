package com.ilkinmehdiyev.parceldelivery.order.service.impls;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.ChangeOrderCourierRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.ChangeOrderStatusRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ChangeOrderCourierResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ChangeOrderStatusResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.GetAllOrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.enums.UserRole;
import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;
import com.ilkinmehdiyev.parceldelivery.order.domain.repository.OrderRepository;
import com.ilkinmehdiyev.parceldelivery.order.service.AdminOrderService;
import com.ilkinmehdiyev.parceldelivery.order.service.OrderService;
import com.ilkinmehdiyev.parceldelivery.order.utility.EntityUtility;
import com.ilkinmehdiyev.parceldelivery.order.utility.ThreadLocalStorage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {
  private final OrderService orderService;
  private final OrderRepository orderRepository;

  @Override
  public ChangeOrderStatusResponse changeOrderStatus(
      Integer orderId, ChangeOrderStatusRequest request) {
    log.info("Starting to change order {} status to {}", orderId, request.orderStatus());

    validateUserAdmin();

    Order orderById = orderService.getOrderById(orderId);
    log.info("Fetched order: {}", orderById);

    orderById.setOrderStatus(request.orderStatus());
    Order saveOrder = orderService.saveOrder(orderById);

    return new ChangeOrderStatusResponse(saveOrder.getId(), saveOrder.getOrderStatus());
  }

  @Override
  public GetAllOrderDetailsResponse getAllOrders() {
    log.info("Starting to fetch all orders for Admin.");

    validateUserAdmin();

    List<Order> orderList = orderRepository.findAll();
    log.info("Found {} orders.", orderList.size());

    List<OrderDetailsResponse> responseList =
        orderList.stream().map(EntityUtility::getOrderDetailsResponse).collect(Collectors.toList());

    return new GetAllOrderDetailsResponse(responseList);
  }

  @Override
  public ChangeOrderCourierResponse changeOrderCourier(Integer orderId, ChangeOrderCourierRequest request) {
    Order orderById = orderService.getOrderById(orderId);
    log.info("Fetched order: {}", orderById);

    validateUserAdmin();

    orderById.setCourierId(request.courierId());
    Order saveOrder = orderService.saveOrder(orderById);
    return new ChangeOrderCourierResponse(saveOrder.getId(), saveOrder.getCourierId());
  }

  private static void validateUserAdmin() {
    String role = ThreadLocalStorage.getSessionUser().getRole();
    if (!UserRole.ADMIN.toString().equalsIgnoreCase(role)) {
      log.error("Only Admin can change status of Parcel Order");
      throw new IllegalArgumentException();
    }
  }
}
