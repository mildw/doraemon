package com.example.redis.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisComponent {
    private final StringRedisTemplate redisTemplate;

    public String get(String id) {
        String key = RedisKey.getKey(id);
        return redisTemplate.opsForValue().get(key);
    }

    public void save(String id, String content) {
        String key = RedisKey.getKey(id);
        redisTemplate.opsForValue().set(key, content);
        redisTemplate.expire(key, Duration.ofMinutes(180L));
    }

    public void delete(String id) {
        String key = RedisKey.getKey(id);
        redisTemplate.delete(key);
    }

}