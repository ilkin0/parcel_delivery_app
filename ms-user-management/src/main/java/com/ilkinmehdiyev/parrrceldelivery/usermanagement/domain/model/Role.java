package com.ilkinmehdiyev.parrrceldelivery.usermanagement.domain.model;

import com.ilkinmehdiyev.parrrceldelivery.usermanagement.constant.RoleConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //  @Enumerated(EnumType.STRING)
    @Column(name = RoleConstant.ROLE)
    private String authority;
}
