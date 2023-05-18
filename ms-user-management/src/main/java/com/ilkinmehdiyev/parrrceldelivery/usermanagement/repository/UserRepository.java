package com.ilkinmehdiyev.parrrceldelivery.usermanagement.repository;

import com.ilkinmehdiyev.parrrceldelivery.usermanagement.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {}
