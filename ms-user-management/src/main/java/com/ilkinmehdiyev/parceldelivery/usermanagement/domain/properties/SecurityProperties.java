package com.ilkinmehdiyev.parceldelivery.usermanagement.domain.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
  private String secretKey;
  private long tokenValidityInSeconds;
  private long tokenValidityInSecondsForRememberMe;
  //
  //  private final JwtProperties jwtProperties = new JwtProperties();
  //
  //  @Getter
  //  @Setter
  //  public static class JwtProperties {
  //
  //    private String secret;
  //    private long tokenValidityInSeconds;
  //    private long tokenValidityInSecondsForRememberMe;
  //  }
}
