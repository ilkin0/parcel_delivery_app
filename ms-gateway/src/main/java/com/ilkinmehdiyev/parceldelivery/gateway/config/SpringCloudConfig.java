package com.ilkinmehdiyev.parceldelivery.gateway.config;

import static com.ilkinmehdiyev.parceldelivery.gateway.constant.ConfigurationConstants.ROOT_DOMAIN;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder) {
    return routeLocatorBuilder
        .routes()
        .route("user-management-service", r -> r.path("/user/**").uri(ROOT_DOMAIN.concat(":8081")))
        .route("order-service", r -> r.path("/order/**").uri(ROOT_DOMAIN.concat(":8082")))
        .build();
  }
}
