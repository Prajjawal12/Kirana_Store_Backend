package com.kirana.register.kirana_store_register.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
  private static final String SECRET_KEY = "iuebfuewbfoiewubfoiawfniowbnfiuowebfuiewbfiub12iuob4io21bn2iobrio32n3ion";
  private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  public String generateToken(String username, String roles) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .claim("authorities", roles)
        .signWith(getKey())
        .compact();
  }

  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public String getRolesFromToken(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    return String.valueOf(claims.get("authorities"));
  }

  public boolean validateToken(String token, String username) {
    String extractedUsername = getUsernameFromToken(token);
    return (extractedUsername.equals(username) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    return claims.getExpiration().before(new Date());
  }
}
