package com.ilkinmehdiyev.parceldelivery.usermanagement.domain.model;

import com.ilkinmehdiyev.parceldelivery.usermanagement.constant.ApplicationConstants;
import com.ilkinmehdiyev.parceldelivery.usermanagement.constant.AuditConstant;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

    @Column(name = AuditConstant.CREATED_BY)
    private Integer createdBy;

    @Column(name = AuditConstant.UPDATED_BY)
    private Integer updatedBy;

    @Column(name = AuditConstant.CREATED_DATE)
    private Date createdDate;

    @Column(name = AuditConstant.UPDATED_DATE)
    private Date updatedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = updatedDate = new Date();
        createdBy = updatedBy = ApplicationConstants.SYSTEM_USER_ID;
        isActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = new Date();
        updatedBy = ApplicationConstants.SYSTEM_USER_ID;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
