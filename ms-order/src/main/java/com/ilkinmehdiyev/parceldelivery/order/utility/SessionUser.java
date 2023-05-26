package com.ilkinmehdiyev.parceldelivery.order.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser {

  private Integer userId;
  private String role;
}
