package com.ilkinmehdiyev.parceldelivery.usermanagement.repository;

import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.model.User;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);

    Optional<UserDetails> findByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
