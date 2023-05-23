package com.ilkinmehdiyev.parceldelivery.order.domain.model;

import com.ilkinmehdiyev.parceldelivery.order.constant.ApplicationConstants;
import com.ilkinmehdiyev.parceldelivery.order.constant.AuditConstant;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
public class Audit {

  @Column(name = AuditConstant.IS_ACTIVE)
  private Boolean isActive;

  @Column(name = AuditConstant.CREATED_DATE)
  private LocalDateTime createdDate;

  @Column(name = AuditConstant.UPDATED_DATE)
  private LocalDateTime updatedDate;

  @PrePersist
  protected void onCreate() {
    createdDate = updatedDate = LocalDateTime.now();
    isActive = true;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedDate = LocalDateTime.now();
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }
}
