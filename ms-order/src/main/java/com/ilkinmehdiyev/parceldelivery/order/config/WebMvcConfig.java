package com.ilkinmehdiyev.parceldelivery.order.config;

import com.ilkinmehdiyev.parceldelivery.order.interceptor.SessionUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SessionUserInterceptor());
  }
}
