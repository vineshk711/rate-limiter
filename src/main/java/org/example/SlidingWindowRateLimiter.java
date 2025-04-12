package org.example;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.*;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final int allowedRequests;
    private final long windowSizeMillis;
    private final Map<Integer, Deque<Long>> userReqLogs = new HashMap<>();

    public SlidingWindowRateLimiter(int allowedRequests, long windowSizeMillis) {
        this.allowedRequests = allowedRequests;
        this.windowSizeMillis = windowSizeMillis;
    }

    @Override
    public boolean isAllowed(Integer userId) {
        long now = Instant.now().toEpochMilli();
        userReqLogs.putIfAbsent(userId, new ArrayDeque<>());
        Deque<Long> userLogs = userReqLogs.get(userId);


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
