package com.wjh.controller;

import com.wjh.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CacheController {

    private final RedisService redisService;

    @DeleteMapping("/clear")
    public void clearAllCache() {
        this.redisService.clearCache();
    }
}
