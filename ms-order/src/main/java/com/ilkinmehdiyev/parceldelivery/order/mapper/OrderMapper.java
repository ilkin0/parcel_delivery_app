package com.ilkinmehdiyev.parceldelivery.order.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.ilkinmehdiyev.parceldelivery.order.domain.dto.request.CreateOrderRequest;
import com.ilkinmehdiyev.parceldelivery.order.domain.dto.response.CreateOrderResponse;
import com.ilkinmehdiyev.parceldelivery.order.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface OrderMapper {

  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  Order toOrder(CreateOrderRequest createOrderRequest);

  CreateOrderResponse toCreateOrderResponse(Order order);
}
