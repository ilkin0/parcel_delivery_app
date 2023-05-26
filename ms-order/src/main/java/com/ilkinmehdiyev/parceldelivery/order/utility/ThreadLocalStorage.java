package com.ilkinmehdiyev.parceldelivery.order.utility;

public class ThreadLocalStorage {

  private static InheritableThreadLocal<SessionUser> sessionUserInheritableThreadLocal =
      new InheritableThreadLocal<>();

  public static void setSessionUser(SessionUser sessionUser) {
    sessionUserInheritableThreadLocal.set(sessionUser);
  }

  public static SessionUser getSessionUser() {
    return sessionUserInheritableThreadLocal.get();
  }

  public static void updateSessionUser(final Integer userId, final String role) {
    sessionUserInheritableThreadLocal.get().setUserId(userId);
    sessionUserInheritableThreadLocal.get().setRole(role);
  }
}
