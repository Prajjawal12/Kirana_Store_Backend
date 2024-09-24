package com.kirana.register.kirana_store_register.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for registering interceptors.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Autowired
  private RateLimitingConfig rateLimitingConfig;

  /**
   * Adds interceptors to the application's request processing pipeline.
   *
   * @param registry the InterceptorRegistry to which interceptors can be added
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(rateLimitingConfig)
        .addPathPatterns("/api/auth/**", "/api/reports/**", "/api/transactions/**");
  }
}
