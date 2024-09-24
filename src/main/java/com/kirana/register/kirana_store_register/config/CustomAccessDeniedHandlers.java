package com.kirana.register.kirana_store_register.config;

import java.io.IOException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom Access Denied Handler that handles access denial exceptions.
 * It returns a 403 Forbidden status with a custom message.
 */
@Component
public class CustomAccessDeniedHandlers implements AccessDeniedHandler {

  /**
   * Handles the access denied scenario by sending a 403 Forbidden response.
   *
   * @param request               the HttpServletRequest
   * @param response              the HttpServletResponse
   * @param accessDeniedException the AccessDeniedException thrown
   * @throws IOException      if an input or output exception occurs
   * @throws ServletException if the request for the GET could not be handled
   */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      org.springframework.security.access.AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.getWriter().write("Access Denied: You do not have the necessary permissions to perform this action.");
  }
}
