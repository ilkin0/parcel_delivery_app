package com.ilkinmehdiyev.parceldelivery.auth.service.impls;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

  //    private final Set<ClaimSetProvider> claimSetProviders;
  //    private final Set<ClaimProvider> claimProviders;

  @Value("${security.jwt.secret-key}")
  private String jwtSecretKey;

  private Key key;

  @PostConstruct
  public void init() {
    byte[] keyBytes;
    keyBytes = Decoders.BASE64.decode(jwtSecretKey);
    key = Keys.hmacShaKeyFor(keyBytes);
  }

  public Claims parseToken(String token) {
    String bearerToken = getBearerToken(token);
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(bearerToken).getBody();
  }

  //    public String issueToken(Authentication authentication, Duration duration) {
  //        log.trace("Issue JWT token to {} for {}", authentication.getPrincipal(), duration);
  //        final JwtBuilder jwtBuilder = Jwts.builder()
  //                .setSubject(authentication.getName())
  //                .setIssuedAt(new Date())
  //                .setExpiration(Date.from(Instant.now().plus(duration)))
  //                .setHeader(Map.of("type", "JWT"))
  //                .signWith(key, SignatureAlgorithm.HS512);
  //
  //        addClaimsSets(jwtBuilder, authentication);
  //        addClaims(jwtBuilder, authentication);
  //        return jwtBuilder.compact();
  //    }

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

  private String getBearerToken(String token) {
    return token.startsWith("Bearer ") ? token.substring("Bearer ".length()) : token;
  }
}
