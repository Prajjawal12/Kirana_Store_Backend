package com.kirana.register.kirana_store_register.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rate Limiting configuration that implements a token bucket algorithm
 * to limit the number of requests from clients.
 */
@Component
public class RateLimitingConfig implements HandlerInterceptor {
  private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

  /**
   * Creates a new Bucket with a refill rate of 10 requests per minute.
   *
   * @return a new Bucket instance
   */
  private Bucket createNewBucket() {
    Refill refill = Refill.greedy(10, Duration.ofMinutes(1));
    Bandwidth limit = Bandwidth.classic(10, refill);
    return Bucket.builder().addLimit(limit).build();
  }

  /**
   * Intercepts requests to check if the rate limit has been exceeded.
   *
   * @param request  the HttpServletRequest
   * @param response the HttpServletResponse
   * @param handler  the handler for the request
   * @return true if the request is allowed, false otherwise
   * @throws IOException if an input or output exception occurs
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws IOException {
    String clientIP = request.getRemoteAddr();
    Bucket bucket = cache.computeIfAbsent(clientIP, k -> createNewBucket());

    if (bucket.tryConsume(1)) {
      return true;
    } else {
      response.setStatus(429);
      response.getWriter().write("Too many requests - Rate limit exceeded");
      return false;
    }
  }
}
