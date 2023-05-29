package com.ilkinmehdiyev.parceldelivery.order.service;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.ChangeOrderCourierRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.ChangeOrderStatusRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ChangeOrderCourierResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ChangeOrderStatusResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.GetAllOrderDetailsResponse;

public interface AdminOrderService {
  ChangeOrderStatusResponse changeOrderStatus(Integer orderId, ChangeOrderStatusRequest request);

  GetAllOrderDetailsResponse getAllOrders();

  ChangeOrderCourierResponse changeOrderCourier(Integer orderId, ChangeOrderCourierRequest request);
}
