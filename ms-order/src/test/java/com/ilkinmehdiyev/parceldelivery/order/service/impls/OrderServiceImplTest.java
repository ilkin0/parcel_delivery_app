package com.ilkinmehdiyev.parceldelivery.order.service.impls;

import static com.ilkinmehdiyev.parceldelivery.order.TestConstants.DEST_1;
import static com.ilkinmehdiyev.parceldelivery.order.TestConstants.DEST_2;
import static com.ilkinmehdiyev.parceldelivery.order.TestConstants.DEST_NEW;
import static com.ilkinmehdiyev.parceldelivery.order.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
import com.ilkinmehdiyev.parceldelivery.order.utility.SessionUser;
import com.ilkinmehdiyev.parceldelivery.order.utility.ThreadLocalStorage;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

  @Mock private OrderRepository orderRepository;

  private Order order;

  @InjectMocks private OrderServiceImpl orderService;

  @BeforeEach
  public void init() {
    ThreadLocalStorage.setSessionUser(new SessionUser(USER_ID, UserRole.USER.toString()));
    order = new Order(null, USER_ID, null, DEST_1, OrderStatus.CREATED, null);
  }

  @Test
  public void testCreateOrder() {
    CreateOrderRequest request = new CreateOrderRequest(USER_ID, DEST_1);
    Order order = new Order(null, USER_ID, null, DEST_1, OrderStatus.CREATED, null);

    when(orderRepository.save(any(Order.class))).thenReturn(order);

    CreateOrderResponse response = orderService.createOrder(request);
    assertEquals(USER_ID, response.userId());
    assertEquals(DEST_1, response.destination());
    assertEquals(OrderStatus.CREATED, response.orderStatus());
  }

  @Test
  public void testGetOrderById() {
    when(orderRepository.findById(1)).thenReturn(Optional.of(order));

    Order result = orderService.getOrderById(1);
    assertEquals(order, result);
  }

  @Test
  public void testGetOrderByIdNotFound() {
    when(orderRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(1));
  }

  @Test
  public void testGetOrderByIdWhenUserRoleNotOk() {
    ThreadLocalStorage.getSessionUser().setRole(UserRole.COURIER.toString());

    assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(1));
  }

  @Test
  public void testGetAllOrders() {
    Order order1 = new Order(null, USER_ID, null, DEST_1, OrderStatus.CREATED, null);
    Order order2 = new Order(null, USER_ID, null, DEST_2, OrderStatus.CREATED, null);
    List<Order> orders = Arrays.asList(order1, order2);

    when(orderRepository.getAllByUserId(USER_ID)).thenReturn(orders);

    GetAllOrderDetailsResponse response = orderService.getAllOrders();
    assertEquals(2, response.details().size());
  }

  @Test
  public void testSaveOrder() {
    when(orderRepository.save(order)).thenReturn(order);

    Order result = orderService.saveOrder(order);
    assertEquals(order, result);
  }

  @Test
  public void testSaveOrderException() {
    when(orderRepository.save(order)).thenThrow(new IllegalArgumentException());

    assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(order));
  }

  @Test
  public void testGetOrderDetailsById() {
    when(orderRepository.findById(1)).thenReturn(Optional.of(order));

    OrderDetailsResponse result = orderService.getOrderDetailsById(1);
    assertEquals(order.getCourierId(), result.courierId());
    assertEquals(order.getOrderStatus(), result.orderStatus());
    assertEquals(order.getDestination(), result.destination());
    assertEquals(order.getDeliveryDate(), result.deliveryDate());
  }

  @Test
  public void testChangeDestination() {
    OrderDestinationRequest request = new OrderDestinationRequest(DEST_NEW);
    when(orderRepository.findById(1)).thenReturn(Optional.of(order));
    when(orderRepository.save(order)).thenReturn(order);

    OrderDestinationResponse result = orderService.changeDestination(1, request);
    assertEquals(order.getDestination(), result.newDestination());
  }

  @Test
  public void testChangeDestinationWhenOrderNotCreated() {
    OrderDestinationRequest request = new OrderDestinationRequest(DEST_NEW);
    order.setOrderStatus(OrderStatus.IN_PROGRESS);
    when(orderRepository.findById(1)).thenReturn(Optional.of(order));

    assertThrows(IllegalArgumentException.class, () -> orderService.changeDestination(1, request));
  }

  @Test
  public void testDeleteById() {
    when(orderRepository.deleteOrderById(1)).thenReturn(1);

    OrderCancelResponse result = orderService.deleteById(1);
    assertEquals(Integer.valueOf(1), result.updateCount());
  }
}
