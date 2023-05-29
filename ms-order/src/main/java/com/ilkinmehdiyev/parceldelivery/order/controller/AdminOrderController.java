package com.ilkinmehdiyev.parceldelivery.order.controller;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.ChangeOrderCourierRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.ChangeOrderStatusRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ChangeOrderCourierResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ChangeOrderStatusResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.GetAllOrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ResponseTemplate;
import com.ilkinmehdiyev.parceldelivery.order.service.AdminOrderService;
import com.ilkinmehdiyev.parceldelivery.order.utility.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/v1/orders/admin")
@RestController
@RequiredArgsConstructor
public class AdminOrderController {
  private final AdminOrderService adminOrderService;

  @PutMapping("/{orderId}")
  public ResponseEntity<ResponseTemplate<ChangeOrderStatusResponse>> changeOrderStatus(
      @PathVariable Integer orderId, @RequestBody @Validated ChangeOrderStatusRequest request) {
    ChangeOrderStatusResponse response = adminOrderService.changeOrderStatus(orderId, request);

    return new ResponseEntity<>(
        ResponseUtility.generateResponse(response, HttpStatus.OK.value(), "Order details fetched."),
        HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<ResponseTemplate<GetAllOrderDetailsResponse>> getOrderDetails() {
    GetAllOrderDetailsResponse response = adminOrderService.getAllOrders();

    return new ResponseEntity<>(
        ResponseUtility.generateResponse(response, HttpStatus.OK.value(), "Orders fetched."),
        HttpStatus.OK);
  }

  @PutMapping("/courier/{orderId}")
  public ResponseEntity<ResponseTemplate<ChangeOrderCourierResponse>> changeOrderCourier(
      @PathVariable Integer orderId, ChangeOrderCourierRequest request) {
    ChangeOrderCourierResponse response = adminOrderService.changeOrderCourier(orderId, request);

    return new ResponseEntity<>(
        ResponseUtility.generateResponse(
            response, HttpStatus.OK.value(), "Order's courier have been updated."),
        HttpStatus.OK);
  }
}
