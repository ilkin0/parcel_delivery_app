package com.ilkinmehdiyev.parceldelivery.order.domain.model;

import com.ilkinmehdiyev.parceldelivery.order.constant.OrderConstant;
import com.ilkinmehdiyev.parceldelivery.order.domain.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = OrderConstant.TABLE_NAME)
public class Order extends Audit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = OrderConstant.ID)
  private Integer id;

  @Column(name = OrderConstant.USER_ID)
  private Integer userId;

  @Column(name = OrderConstant.COURIER_ID)
  private Integer courierId;

  @Column(name = OrderConstant.DESTINATION)
  private String destination;

  @Column(name = OrderConstant.ORDER_STATUS)
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;
}
