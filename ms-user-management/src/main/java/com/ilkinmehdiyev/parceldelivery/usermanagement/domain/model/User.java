package com.ilkinmehdiyev.parceldelivery.usermanagement.domain.model;

import com.ilkinmehdiyev.parceldelivery.usermanagement.constant.RoleConstant;
import com.ilkinmehdiyev.parceldelivery.usermanagement.constant.UserConstant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = UserConstant.TABLE_NAME)
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String password;

  private String name;

  private String username;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = UserConstant.ROLE_ID, referencedColumnName = RoleConstant.ID)
  private Role role;

  private boolean accountNonExpired;

  private boolean accountNonLocked;

  private boolean credentialsNonExpired;

  private boolean enabled;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(getRole().getName().toString()));
  }
}
