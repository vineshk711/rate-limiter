package org.example;

public interface RateLimiter {
    boolean isAllowed(Integer userId);
}
