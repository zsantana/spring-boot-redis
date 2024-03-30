package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private static Logger logger = LoggerFactory.getLogger(CacheService.class);
    private static final long CACHE_EXPIRATION_TIME = 10; // Tempo de expiração do cache em minutos

    static class RedisOperationException extends RuntimeException {
        public RedisOperationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public CacheData verificarExistenciaECache(String chaveControle) {

        try {
            CacheData cachedData = (CacheData)redisTemplate.opsForValue().get(chaveControle);

            if (cachedData == null) {
                var data = new CacheData(chaveControle, UUID.randomUUID().toString());
                logger.info("### Gerando novo cache v1 {} ", UUID.randomUUID().toString());
                armazenarCache(chaveControle, data);
                return data;
            } else {
                return cachedData;
            }

        } catch (Exception e) {
            throw new RedisOperationException("Erro ao recuperar dados do Redis: " + e.getMessage(), e);
        }
        
        
    }


    private void armazenarCache(String chaveControle, CacheData data) {
        try {
            redisTemplate.opsForValue().set(chaveControle, data, CACHE_EXPIRATION_TIME, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RedisOperationException("Erro ao armazenar dados do Redis: " + e.getMessage(), e);
        }
        
    }


    public synchronized CacheData verificarExistenciaECache2(String chaveControle) {

        try {

            CacheData cachedData = (CacheData) redisTemplate.opsForValue().get(chaveControle);
        
            if (cachedData == null) {
                var data = new CacheData(chaveControle, UUID.randomUUID().toString());
                if (redisTemplate.opsForValue().setIfAbsent(chaveControle, data)) {
                    logger.info("### Gerando novo cache v2 {} ", UUID.randomUUID().toString());
                    redisTemplate.expire(chaveControle, CACHE_EXPIRATION_TIME, TimeUnit.SECONDS);
                    return data;
                } else {
                    // Outra thread inseriu os dados antes desta, obtendo os dados atualizados do cache
                    return (CacheData) redisTemplate.opsForValue().get(chaveControle);
                }
            } else {
                return cachedData;
            }
            
        } catch (Exception e) {
            throw new RedisOperationException("Erro ao recuperar dados do Redis: " + e.getMessage(), e);
        }

        
        
    }


    @Cacheable(value = "cacheData", key = "#chaveControle")
    public CacheData verificarExistenciaECache3(String chaveControle) {
        logger.info("### Gerando novo cache v3 {} ", UUID.randomUUID().toString());
        return new CacheData(chaveControle, UUID.randomUUID().toString());
    }


    
}
