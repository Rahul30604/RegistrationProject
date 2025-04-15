package com.Service1.Registration.RateLimiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final int MAX_REQUESTS = 5;
    private final int TIME_WINDOW_SECONDS = 60;

    public boolean isAllowed(String clientId) {
        String key = "rate_limit:" + clientId;

        Long currentCount = redisTemplate.opsForValue().increment(key);
        if (currentCount == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(TIME_WINDOW_SECONDS));
        }

        return currentCount <= MAX_REQUESTS;
    }
}
