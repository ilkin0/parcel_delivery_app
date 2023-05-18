package com.ilkinmehdiyev.parrrceldelivery.usermanagement.domain.model;

import com.ilkinmehdiyev.parrrceldelivery.usermanagement.constant.RoleConstant;
import com.ilkinmehdiyev.parrrceldelivery.usermanagement.domain.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = RoleConstant.TABLE_NAME)
public class Role implements GrantedAuthority {

  @Id @GeneratedValue private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = RoleConstant.ROLE)
  private UserRole authority;
}
