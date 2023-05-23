package com.ilkinmehdiyev.parceldelivery.order.domain.repository;

import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {}
