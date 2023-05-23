package com.ilkinmehdiyev.parceldelivery.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCredential {
  private Integer userId;
  private String username;
  private String password;
}
