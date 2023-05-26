package com.ilkinmehdiyev.parceldelivery.order.controller;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.CreateOrderRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.CreateOrderResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.ResponseTemplate;
import com.ilkinmehdiyev.parceldelivery.order.service.OrderService;
import com.ilkinmehdiyev.parceldelivery.order.utility.ResponseUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
}
