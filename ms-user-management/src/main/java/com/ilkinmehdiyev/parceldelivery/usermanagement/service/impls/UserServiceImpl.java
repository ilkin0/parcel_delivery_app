package com.ilkinmehdiyev.parceldelivery.usermanagement.service.impls;

import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.request.SignUpRequestDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.response.SignUpResponseDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.response.UserInfoResponse;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.enums.UserRole;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.model.Role;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.model.User;
import com.ilkinmehdiyev.parceldelivery.usermanagement.repository.UserRepository;
import com.ilkinmehdiyev.parceldelivery.usermanagement.service.UserService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public SignUpResponseDto registerUser(SignUpRequestDto requestDto) {
    validateIfUserExist(requestDto.username());
    User newUser = getNewUser(requestDto);
    User savedUser = userRepository.save(newUser);
    return new SignUpResponseDto(savedUser.getId());
  }

  @Override
  public UserInfoResponse searchUserEmail(String email) {
    User user =
        userRepository
            .findUserByUsername(email)
            .orElseThrow(
                () -> {
                  log.error("User not found with: {} email!", email);
                  return new IllegalArgumentException(
                      String.format("User not found with: %s email!", email));
                });

    return new UserInfoResponse(String.valueOf(user.getId()), user.getRole().getName().toString());
  }

  private User getNewUser(SignUpRequestDto requestDto) {
    Role role = new Role();
    role.setName(
        Arrays.stream(UserRole.values())
            .filter(userRole -> userRole.name().equalsIgnoreCase(requestDto.name()))
            .findFirst()
            .orElse(UserRole.USER));

    return User.builder()
        .name(requestDto.name())
        .username(requestDto.username())
        .role(role)
        .password(getEncodedPassword(requestDto.password()))
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .enabled(true)
        .build();
  }

  private String getEncodedPassword(String password) {
    return passwordEncoder.encode(password);
  }

  private void validateIfUserExist(String username) {
    boolean exists = userRepository.existsByUsername(username);
    if (exists) {
      log.error("User with {} username already exists", username);
      throw new IllegalArgumentException(
          String.format("User with %s username already exists", username));
    }
  }
}
