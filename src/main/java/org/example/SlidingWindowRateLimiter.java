package org.example;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final int allowedRequests;
    private final long windowSizeMillis;
    private final Map<Integer, Deque<Long>> userReqLogs = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(int allowedRequests, long windowSizeMillis) {
        this.allowedRequests = allowedRequests;
        this.windowSizeMillis = windowSizeMillis;
    }

    @Override
    public boolean isAllowed(Integer userId) {
        Deque<Long> userLogs = userReqLogs.computeIfAbsent(userId, k -> new  ConcurrentLinkedDeque<>());

        synchronized (userLogs) {
            long now = Instant.now().toEpochMilli();

            while(!userLogs.isEmpty() && (now - userLogs.peekFirst() >= windowSizeMillis)) {
                userLogs.pollFirst();
            }

            if(userLogs.size() < allowedRequests) {
                userLogs.addLast(now);
                return true;
            } else {
                return false;
            }
        }
    }
}
