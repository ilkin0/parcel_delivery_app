package com.ilkinmehdiyev.parceldelivery.order.interceptor;

import com.ilkinmehdiyev.parceldelivery.order.utility.SessionUser;
import com.ilkinmehdiyev.parceldelivery.order.utility.ThreadLocalStorage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class SessionUserInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String userId = request.getHeader("userId");
    String role = request.getHeader("role");
    SessionUser sessionUser = new SessionUser();

    sessionUser.setUserId(userId != null ? Integer.parseInt(userId) : 0);
    sessionUser.setRole(role);

    log.info("Current userId : {}", userId);
    log.info("Current role : {}", role);
    log.info("Current Thread Id : {}", Thread.currentThread().getId());
    log.info("Current customUser object : {}", sessionUser);

    ThreadLocalStorage.setSessionUser(sessionUser);
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    ThreadLocalStorage.setSessionUser(null);
  }
}
