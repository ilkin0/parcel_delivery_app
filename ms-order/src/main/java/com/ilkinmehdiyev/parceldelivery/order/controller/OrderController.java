package com.ilkinmehdiyev.parceldelivery.order.controller;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.CreateOrderRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.OrderDestinationRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.CreateOrderResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.GetAllOrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderCancelResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderDestinationResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.OrderDetailsResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ResponseTemplate;
import com.ilkinmehdiyev.parceldelivery.order.service.OrderService;
import com.ilkinmehdiyev.parceldelivery.order.utility.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/v1/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<ResponseTemplate<CreateOrderResponse>> createOrder(
      @RequestBody @Validated CreateOrderRequest orderRequest) {
    log.info("Starting Create Order request...");
    CreateOrderResponse response = orderService.createOrder(orderRequest);

    return new ResponseEntity<>(
        ResponseUtility.generateResponse(
            response, HttpStatus.CREATED.value(), "Order successfully created"),
        HttpStatus.CREATED);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<ResponseTemplate<OrderDetailsResponse>> getOrderDetails(
      @PathVariable Integer orderId) {
    OrderDetailsResponse response = orderService.getOrderDetailsById(orderId);

    return new ResponseEntity<>(
        ResponseUtility.generateResponse(response, HttpStatus.OK.value(), "Order details fetched."),
        HttpStatus.OK);
  }

  @PutMapping("/{orderId}")
  public ResponseEntity<ResponseTemplate<OrderDestinationResponse>> changeDestination(
      @PathVariable Integer orderId, @RequestBody @Validated OrderDestinationRequest request) {
    OrderDestinationResponse response = orderService.changeDestination(orderId, request);

    return new ResponseEntity<>(
        ResponseUtility.generateResponse(
            response, HttpStatus.OK.value(), "Order destination has been updated"),
        HttpStatus.OK);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<ResponseTemplate<OrderCancelResponse>> deleteOrder(
      @PathVariable Integer orderId) {
    OrderCancelResponse response = orderService.deleteById(orderId);

    return new ResponseEntity<>(
        ResponseUtility.generateResponse(
            response, HttpStatus.ACCEPTED.value(), "Order has been deleted"),
        HttpStatus.ACCEPTED);
  }

  @GetMapping()
  public ResponseEntity<ResponseTemplate<GetAllOrderDetailsResponse>> getOrderDetails() {
    GetAllOrderDetailsResponse response = orderService.getAllOrders();

    return new ResponseEntity<>(
        ResponseUtility.generateResponse(response, HttpStatus.OK.value(), "Orders fetched."),
        HttpStatus.OK);
  }
}
