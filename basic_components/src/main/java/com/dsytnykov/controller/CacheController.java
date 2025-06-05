package com.dsytnykov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/management/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CacheManager cacheManager;

    @GetMapping
    public Map<String, Object> getCacheStats() {
        Map<String, Object> stats = new HashMap<>();

        cacheManager.getCacheNames().forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            if(cache instanceof CaffeineCache caffeineCache) {
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
                stats.put(cacheName, Map.of(
                        "estimatedSize", nativeCache.estimatedSize(),
                        "stats", nativeCache.stats()));
            }
        });
        return stats;
    }

    @DeleteMapping("/{cacheName}")
    public ResponseEntity<Void> clearCache(@PathVariable String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if(cache != null) {
            cache.clear();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            if(cache != null) {
                cache.clear();
            }
        });
        return ResponseEntity.ok().build();
    }
}
