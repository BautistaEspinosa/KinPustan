package com.kinpustan.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager();
    cacheManager.setCaffeine(caffeineCacheBuilder());
    return cacheManager;
  }

  private Caffeine<Object, Object> caffeineCacheBuilder() {
    return Caffeine.newBuilder()
        .initialCapacity(100)
        .maximumSize(500)
        .expireAfterAccess(Duration.ofMinutes(30))
        .expireAfterWrite(Duration.ofHours(1))
        .recordStats();
  }

  @Bean("productosCache")
  public CacheManager productosCacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager("productos", "productos-categoria");
    cacheManager.setCaffeine(Caffeine.newBuilder()
        .initialCapacity(50)
        .maximumSize(200)
        .expireAfterWrite(Duration.ofMinutes(15))
        .recordStats());
    return cacheManager;
  }

  @Bean("categoriasCache")
  public CacheManager categoriasCacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager("categorias");
    cacheManager.setCaffeine(Caffeine.newBuilder()
        .initialCapacity(20)
        .maximumSize(100)
        .expireAfterWrite(Duration.ofMinutes(30))
        .recordStats());
    return cacheManager;
  }
}