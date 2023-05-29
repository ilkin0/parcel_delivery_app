package com.ilkinmehdiyev.parceldelivery.order.domain.repository;

import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Integer> {

  @Modifying
  @Query(
      "update Order o set o.isActive = false,"
          + " o.orderStatus = com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus.CANCELED"
          + " where o.id = :orderId")
  Integer deleteOrderById(@Param("orderId") Integer orderId);

  List<Order> getAllByUserId(Integer userId);
}
