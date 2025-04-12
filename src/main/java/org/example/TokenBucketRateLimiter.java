package org.example;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter {

    private final Map<Integer, TokenBucket> tokenMap = new ConcurrentHashMap<>();


    @Override
    public boolean isAllowed(Integer userId) {
        tokenMap.putIfAbsent(userId, getTokenBucket(userId));
        TokenBucket bucket = tokenMap.get(userId);
        return bucket.tryConsume();
    }

    private TokenBucket getTokenBucket(Integer userId) {
        //load from some DB/config
        return new TokenBucket(5, 1, 5, Instant.now().toEpochMilli());
    }
}
