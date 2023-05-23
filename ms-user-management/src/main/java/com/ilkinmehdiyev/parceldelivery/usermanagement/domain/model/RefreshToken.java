package com.ilkinmehdiyev.parceldelivery.usermanagement.domain.model;

import com.ilkinmehdiyev.parceldelivery.usermanagement.constant.RefreshTokenConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = RefreshTokenConstant.TABLE_NAME)
public class RefreshToken {
  public static final String TABLE_NAME = "refresh_token";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = RefreshTokenConstant.ID)
  private Integer id;

  @Column(name = RefreshTokenConstant.USERNAME)
  private String username;

  @Column(name = RefreshTokenConstant.TOKEN)
  private String token;

  @Column(name = RefreshTokenConstant.EXPIRY_DATE)
  private LocalDateTime expiryDate;

  @Column(name = RefreshTokenConstant.IS_VALID)
  private boolean isValid;
}
