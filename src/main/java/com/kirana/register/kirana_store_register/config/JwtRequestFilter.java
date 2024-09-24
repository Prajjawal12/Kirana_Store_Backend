package com.kirana.register.kirana_store_register.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.kirana.register.kirana_store_register.utils.JwtUtil;

/**
 * Filter for validating JSON Web Tokens (JWT) in incoming requests.
 * It sets the authentication in the Security Context if the token is valid.
 */
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  /**
   * Constructs a new JwtRequestFilter with the specified JwtUtil.
   *
   * @param jwtUtil the utility class for JWT operations
   */
  public JwtRequestFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  /**
   * Filters incoming requests, validating the JWT and setting the authentication
   * context.
   *
   * @param request     the HttpServletRequest
   * @param response    the HttpServletResponse
   * @param filterChain the filter chain
   * @throws ServletException if the request for the GET could not be handled
   * @throws IOException      if an input or output exception occurs
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String jwt = request.getHeader("Authorization");

    if (jwt != null && jwt.startsWith("Bearer ")) {
      jwt = jwt.substring(7);
      try {
        String username = jwtUtil.getUsernameFromToken(jwt);
        String roles = jwtUtil.getRolesFromToken(jwt);

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
      } catch (Exception e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
        return;
      }
    }

    filterChain.doFilter(request, response);
  }
}
