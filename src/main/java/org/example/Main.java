package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        RateLimiter slidingWindowRateLimiter = new SlidingWindowRateLimiter(5, 5000);
        for(int i = 1; i<=7; i++) {
            boolean isAllowed = slidingWindowRateLimiter.isAllowed(1);
//            System.out.println("is user "+ i + " allowed :" + isAllowed );
            Thread.sleep(100);
        }

        RateLimiter tokenBucketRateLimiter = new TokenBucketRateLimiter();
        for(int i = 1; i<=7; i++) {
            boolean isAllowed = tokenBucketRateLimiter.isAllowed(1);
            System.out.println("is user "+ i + " allowed :" + isAllowed );
            Thread.sleep(100);
        }
    }
}