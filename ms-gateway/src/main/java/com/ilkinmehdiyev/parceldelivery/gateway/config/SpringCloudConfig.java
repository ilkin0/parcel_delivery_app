package com.ilkinmehdiyev.parceldelivery.gateway.config;

import static com.ilkinmehdiyev.parceldelivery.gateway.constant.ConfigurationConstants.API_V1;

import com.ilkinmehdiyev.parceldelivery.gateway.constant.ConfigurationConstants;
import com.ilkinmehdiyev.parceldelivery.gateway.security.filter.JwtAuthenticationFilter;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SpringCloudConfig {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Value("${ms.user-mng.root}")
  private String msUserMngRoot;

  @Value("${ms.auth.root}")
  private String msAuthRoot;

  @Value("${ms.order.root}")
  private String msOrderRoot;

  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder) {
    return routeLocatorBuilder
        .routes()
        .route(
            ConfigurationConstants.AUTH_SERVICE_ID,
            getRoute(ConfigurationConstants.AUTH_SERVICE_ROOT, msAuthRoot))
        .route(
            ConfigurationConstants.USER_MANAGEMENT_SERVICE_ID,
            getRoute(ConfigurationConstants.USER_MANAGEMENT_SERVICE_ROOT, msUserMngRoot))
        .route(
            ConfigurationConstants.ORDER_SERVICE_ID,
            getRoute(ConfigurationConstants.ORDERS_SERVICE_ROOT, msOrderRoot))
        //        .route("order-service", r -> r.path("/order/**").uri(ROOT_DOMAIN.concat(":8082")))
        //        .route(
        //            "go-test-server",
        //            r ->
        //                r.path("/hello-go/**")
        //                    .filters(filterSpec -> getGatewayFilterSpec(filterSpec, "/hello-go"))
        //                    .uri(ROOT_DOMAIN.concat(":80")))
        .build();
  }

  private Function<PredicateSpec, Buildable<Route>> getRoute(String root, String uri) {
    return r ->
        r.path(root.concat("/**"))
            .filters(filterSpec -> getGatewayFilterSpec(filterSpec, root))
            .uri(uri);
  }

  private GatewayFilterSpec getGatewayFilterSpec(GatewayFilterSpec f, String serviceUri) {
    return f.rewritePath(
            serviceUri.concat("(?<segment>.*)"), API_V1.concat(serviceUri).concat("${segment}"))
        .filter(jwtAuthenticationFilter);
  }
}
