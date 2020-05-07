package com.springWithRedis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class RedisConfig {


    @Autowired
    private CacheManager cacheManager;


    @PreDestroy
    public void evictAllCacheValues() {
        cacheManager.getCacheNames()
                .parallelStream()
                .forEach(n -> cacheManager.getCache(n).clear());

    }
}