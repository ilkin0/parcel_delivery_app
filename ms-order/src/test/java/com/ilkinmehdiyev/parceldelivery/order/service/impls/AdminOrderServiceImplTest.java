package com.ilkinmehdiyev.parceldelivery.order.service.impls;

import static com.ilkinmehdiyev.parceldelivery.order.TestConstants.COURIER_ID;
import static com.ilkinmehdiyev.parceldelivery.order.TestConstants.DEST_1;
import static com.ilkinmehdiyev.parceldelivery.order.TestConstants.DEST_2;
import static com.ilkinmehdiyev.parceldelivery.order.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.ChangeOrderCourierRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.ChangeOrderStatusRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.CreateOrderRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ChangeOrderCourierResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ChangeOrderStatusResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.CreateOrderResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.GetAllOrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus;
import com.ilkinmehdiyev.parceldelivery.order.domain.enums.UserRole;
import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;
import com.ilkinmehdiyev.parceldelivery.order.domain.repository.OrderRepository;
import com.ilkinmehdiyev.parceldelivery.order.service.OrderService;
import com.ilkinmehdiyev.parceldelivery.order.utility.SessionUser;
import com.ilkinmehdiyev.parceldelivery.order.utility.ThreadLocalStorage;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminOrderServiceImplTest {

  @Mock private OrderService orderService;

  @Mock private OrderRepository orderRepository;

  @InjectMocks private AdminOrderServiceImpl adminOrderService;
  private Order order;

  @BeforeEach
  public void init() {
    ThreadLocalStorage.setSessionUser(new SessionUser(USER_ID, UserRole.ADMIN.toString()));
    order = new Order(null, USER_ID, null, DEST_1, OrderStatus.CREATED, null);
  }

  @Test
  public void testChangeOrderStatus() {
    when(orderService.getOrderById(1)).thenReturn(order);
    when(orderService.saveOrder(order)).thenReturn(order);

    ChangeOrderStatusRequest request = new ChangeOrderStatusRequest(OrderStatus.DELIVERED);
    ChangeOrderStatusResponse response = adminOrderService.changeOrderStatus(1, request);

    assertEquals(order.getId(), response.orderId());
    assertEquals(OrderStatus.DELIVERED, response.orderStatus());
  }

  @Test
  public void testGetAllOrders() {
    Order order1 = new Order(null, USER_ID, COURIER_ID, DEST_1, OrderStatus.CREATED, null);
    Order order2 = new Order(null, USER_ID, COURIER_ID, DEST_2, OrderStatus.CREATED, null);
    List<Order> orders = Arrays.asList(order1, order2);

    when(orderRepository.findAll()).thenReturn(orders);

    GetAllOrderDetailsResponse response = adminOrderService.getAllOrders();
    assertEquals(2, response.details().size());
  }

  @Test
  public void testChangeOrderCourier() {
    when(orderService.getOrderById(1)).thenReturn(order);
    when(orderService.saveOrder(order)).thenReturn(order);

    ChangeOrderCourierRequest request = new ChangeOrderCourierRequest(COURIER_ID);
    ChangeOrderCourierResponse response = adminOrderService.changeOrderCourier(1, request);

    assertEquals(order.getId(), response.orderId());
    assertEquals(COURIER_ID, response.courierId());
  }

  @Test
  public void testChangeOrderStatusNotAdmin() {
    ThreadLocalStorage.getSessionUser().setRole(UserRole.USER.toString());

    assertThrows(
        IllegalArgumentException.class,
        () ->
            adminOrderService.changeOrderStatus(
                1, new ChangeOrderStatusRequest(OrderStatus.DELIVERED)));
  }
}
