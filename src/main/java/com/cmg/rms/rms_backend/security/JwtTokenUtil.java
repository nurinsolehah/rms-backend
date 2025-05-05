package com.cmg.rms.rms_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
  @Value("${security.jwt.secret-key}")
  private String secretKey;

  @Value("${security.jwt.expiration}")
  private Long expiration;

  // create a token when users log in which stores username, user role, the time created and
  // expiration time
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> extraClaims = new HashMap<>();

    List<String> userRole =
        userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    extraClaims.put("userRole", userRole);

    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  // use secret key to sign the token
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Claims extractClaim(String token) {
    return extractAllClaims(token);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  // check if username in token matched sign in username and token is not expired
  public Boolean isTokenValid(String username, String token, UserDetails userDetails) {
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  // check if current time is past token expiration time
  private Boolean isTokenExpired(String token) {
    return extractClaim(token).getExpiration().before(new Date());
  }

  // get username from the token
  public String extractUsername(String token) {
    return extractClaim(token).getSubject();
  }

  // get user role from the token
  public List<String> extractRoles(String token) {
    List<?> userRole = extractClaim(token).get("userRole", List.class);
    List<String> result = new ArrayList<>();
    for (Object role : userRole) {
      result.add((String) role);
    }
    return result;
  }
}
