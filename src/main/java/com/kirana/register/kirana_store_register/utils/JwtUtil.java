package com.kirana.register.kirana_store_register.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 */
@Component
public class JwtUtil {
  private static final String SECRET_KEY = "iuebfuewbfoiewubfoiawfniowbnfiuowebfuiewbfiub12iuob4io21bn2iobrio32n3ion";
  private static final long EXPIRATION_TIME = 86400000;

  /**
   * Generates a secret key for signing the JWT.
   *
   * @return the secret key
   */
  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  /**
   * Generates a JWT token with the specified username and roles.
   *
   * @param username the username to include in the token
   * @param roles    the roles to include in the token
   * @return the generated JWT token
   */
  public String generateToken(String username, String roles) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .claim("authorities", roles)
        .signWith(getKey())
        .compact();
  }

  /**
   * Extracts the username from the provided JWT token.
   *
   * @param token the JWT token
   * @return the username extracted from the token
   */
  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  /**
   * Extracts the roles from the provided JWT token.
   *
   * @param token the JWT token
   * @return the roles extracted from the token
   */
  public String getRolesFromToken(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    return String.valueOf(claims.get("authorities"));
  }

  /**
   * Validates the provided JWT token against the specified username.
   *
   * @param token    the JWT token
   * @param username the username to validate against
   * @return true if the token is valid for the username, false otherwise
   */
  public boolean validateToken(String token, String username) {
    String extractedUsername = getUsernameFromToken(token);
    return (extractedUsername.equals(username) && !isTokenExpired(token));
  }

  /**
   * Checks if the provided JWT token has expired.
   *
   * @param token the JWT token
   * @return true if the token is expired, false otherwise
   */
  private boolean isTokenExpired(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    return claims.getExpiration().before(new Date());
  }
}
