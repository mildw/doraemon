package com.example.redis.controller;

import com.example.redis.config.redis.RedisComponent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisComponent redisComponent;

    @GetMapping("/get/{id}")
    public String get(
            @PathVariable String id
    ) {
        return redisComponent.get(id);
    }

    @GetMapping("/save/{id}")
    public void save(
            @PathVariable String id,
            @RequestParam String content
    ) {
        redisComponent.save(id, content);
    }

    @GetMapping("/delete/{id}")
    public void delete(
            @PathVariable String id
    ) {
        redisComponent.delete(id);
    }


}
