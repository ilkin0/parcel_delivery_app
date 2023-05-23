package com.ilkinmehdiyev.parceldelivery.usermanagement.service.impls;

import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.JwtAccessTokenDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.RefreshTokenDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.TokenRefreshResponseDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.dto.request.SignInRequestDto;
import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.model.RefreshToken;
import com.ilkinmehdiyev.parceldelivery.usermanagement.repository.RefreshTokenRepository;
import com.ilkinmehdiyev.parceldelivery.usermanagement.service.AuthService;
import com.ilkinmehdiyev.parceldelivery.usermanagement.service.JwtService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

  public static final Duration DURATION_ONE_DAY = Duration.ofDays(1);

  private final JwtService jwtService;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserDetailsService userDetailsService;

  @Override
  public TokenRefreshResponseDto singInUser(SignInRequestDto requestDto) {
    log.info("Authentication requested by {}", requestDto.username());
    Authentication authenticationToken =
        new UsernamePasswordAuthenticationToken(requestDto.username(), requestDto.password());

    Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtService.issueToken(authentication, DURATION_ONE_DAY);

    JwtAccessTokenDto jwtAccessTokenDto = new JwtAccessTokenDto(jwt);
    RefreshTokenDto refreshTokenDto = issueRefreshToken(authentication);
    // TODO user_login_history logic implementation
    return new TokenRefreshResponseDto(jwtAccessTokenDto, refreshTokenDto);
  }

  private RefreshTokenDto issueRefreshToken(Authentication authentication) {
    RefreshToken refreshToken =
        RefreshToken.builder()
            .username(authentication.getName())
            .token(UUID.randomUUID().toString())
            .expiryDate(LocalDateTime.now().plus(10, ChronoUnit.MINUTES))
            .isValid(true)
            .build();

    refreshTokenRepository.save(refreshToken);
    return new RefreshTokenDto(refreshToken.getToken(), refreshToken.getExpiryDate());
  }

  @Override
  public TokenRefreshResponseDto refreshAccessToken(RefreshTokenDto refreshTokenDto) {
    RefreshToken refreshToken = loadRefreshToken(refreshTokenDto);
    if (refreshToken.isValid() && refreshToken.getExpiryDate().isAfter(LocalDateTime.now())) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(refreshToken.getUsername());
      if (isAccountActive(userDetails)) {
        refreshToken.setValid(false);
        refreshTokenRepository.save(refreshToken);
        Authentication authenticationToken = buildAuthToken(userDetails);
        return new TokenRefreshResponseDto(
            issueAccessToken(authenticationToken), issueRefreshToken(authenticationToken));
      }
    }
    throw new IllegalArgumentException("AUTHENTICATION_FAILED");
  }

  private UsernamePasswordAuthenticationToken buildAuthToken(UserDetails userDetails) {
    return new UsernamePasswordAuthenticationToken(
        userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
  }

  private RefreshToken loadRefreshToken(RefreshTokenDto refreshTokenDto) {
    return refreshTokenRepository
        .findByToken(refreshTokenDto.token())
        .orElseThrow(
            () -> {
              log.trace("Token {} cannot be find", refreshTokenDto.token());
              return new IllegalArgumentException("AUTHENTICATION_FAILED");
            });
  }

  private JwtAccessTokenDto issueAccessToken(Authentication authentication) {
    return new JwtAccessTokenDto(jwtService.issueToken(authentication, DURATION_ONE_DAY));
  }

  private boolean isAccountActive(UserDetails userDetails) {
    return userDetails.isEnabled()
        && userDetails.isAccountNonLocked()
        && userDetails.isAccountNonExpired()
        && userDetails.isCredentialsNonExpired();
  }
}
