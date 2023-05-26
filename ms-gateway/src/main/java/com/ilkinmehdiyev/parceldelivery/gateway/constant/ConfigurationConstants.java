package com.ilkinmehdiyev.parceldelivery.gateway.constant;

public interface ConfigurationConstants {
  String API_V1 = "/api/v1";

  String ROOT_DOMAIN = "http://localhost";

  String USER_MANAGEMENT_SERVICE_ID = "user-management-service";

  String USER_MANAGEMENT_SERVICE_ROOT = "/users";

  String AUTH_SERVICE_ID = "auth-service";

  String AUTH_SERVICE_ROOT = "/auth";

  String ORDER_SERVICE_ID = "order-service";

  String ORDERS_SERVICE_ROOT = "/orders";
  String ORDERS_SERVICE_URI = ROOT_DOMAIN.concat(":8083");

}
