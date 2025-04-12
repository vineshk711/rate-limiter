package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class TokenBucket {
    private int capacity;
    private int refillRate;
    private int tokenCount;
    private long lastRefillTime;

    public boolean tryConsume() {
        refill();
        if(tokenCount > 0) {
            tokenCount--;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = Instant.now().toEpochMilli();
        int tokenToFill = (int) ((now - lastRefillTime) * refillRate) / 1000;
        if(tokenToFill > 0) {
            tokenCount += Math.min(capacity, tokenCount + tokenToFill);
            lastRefillTime = now;
        }
    }


}
