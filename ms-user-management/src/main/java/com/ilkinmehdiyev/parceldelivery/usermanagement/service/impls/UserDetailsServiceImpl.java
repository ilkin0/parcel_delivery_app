package com.ilkinmehdiyev.parceldelivery.usermanagement.service.impls;

import com.ilkinmehdiyev.parceldelivery.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.trace("Authenticating: {}", username);
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("USERNAME_NOT_FOUND"));
  }
}
