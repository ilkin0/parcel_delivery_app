package com.ilkinmehdiyev.parceldelivery.usermanagement.service;

import com.ilkinmehdiyev.parceldelivery.usermanagement.domain.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

  //    private final Set<ClaimSetProvider> claimSetProviders;
  //    private final Set<ClaimProvider> claimProviders;
  private final SecurityProperties securityProperties;

  private Key key;

  @PostConstruct
  public void init() {
    byte[] keyBytes;
    keyBytes = Decoders.BASE64.decode(securityProperties.getSecretKey());
    key = Keys.hmacShaKeyFor(keyBytes);
  }

  public Claims parseToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public String issueToken(Authentication authentication, Duration duration) {
    log.trace("Issue JWT token to {} for {}", authentication.getPrincipal(), duration);

    final JwtBuilder jwtBuilder =
        Jwts.builder()
            .setSubject(authentication.getName())
            .setIssuedAt(new Date())
            .setExpiration(Date.from(Instant.now().plus(duration)))
            .setHeader(Map.of("type", "JWT"))
            .signWith(key, SignatureAlgorithm.HS512);

    addClaims(authentication, jwtBuilder);
    return jwtBuilder.compact();
  }

  private void addClaims(Authentication authentication, JwtBuilder jwtBuilder) {
    Map<String, String> claimsMap = new HashMap<>();

    addAuthoritiesClaims(claimsMap, authentication, jwtBuilder);
    claimsMap.put("username", authentication.getName());

    jwtBuilder.setClaims(claimsMap);
  }

  private void addAuthoritiesClaims(
      Map<String, String> claimsMap, Authentication authentication, JwtBuilder jwtBuilder) {
    authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .forEach(auth -> claimsMap.put("authority", auth));
  }

  //    private JwtBuilder addClaimsSets(JwtBuilder jwtBuilder, Authentication authentication) {
  //        claimSetProviders.forEach(claimSetProvider -> {
  //            final ClaimSet claimSet = claimSetProvider.provide(authentication);
  //            log.trace("Adding claim {}", claimSet);
  //            jwtBuilder.claim(claimSet.getKey(), claimSet.getClaims());
  //        });
  //        return jwtBuilder;
  //    }
  //
  //    private JwtBuilder addClaims(JwtBuilder jwtBuilder, Authentication authentication) {
  //        claimProviders.forEach(claimSetProvider -> {
  //            final Claim claim = claimSetProvider.provide(authentication);
  //            log.trace("Adding claim {}", claim);
  //            jwtBuilder.claim(claim.getKey(), claim.getClaim());
  //        });
  //        return jwtBuilder;
  //    }
}
