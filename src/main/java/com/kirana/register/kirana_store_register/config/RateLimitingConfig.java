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

@Component
public class RateLimitingConfig implements HandlerInterceptor {
  private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

  private Bucket createNewBucket() {
    Refill refill = Refill.greedy(10, Duration.ofMinutes(1));
    Bandwidth limit = Bandwidth.classic(10, refill);
    return Bucket.builder().addLimit(limit).build();
  }

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
