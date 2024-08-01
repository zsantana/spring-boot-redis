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
                logger.info("### obtendo dados do REDIS {}: ", cachedData.toString());
                return cachedData;
            }

        } catch (Exception e) {
            throw new RedisOperationException("### (verificarExistenciaECache) Erro ao recuperar dados do Redis: " + e.getMessage(), e);
        }
        
        
    }


    private void armazenarCache(String chaveControle, CacheData data) {
        try {
            redisTemplate.opsForValue().set(chaveControle, data, CACHE_EXPIRATION_TIME, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RedisOperationException("### Erro ao armazenar dados do Redis: " + e.getMessage(), e);
        }
        
    }


    public CacheData verificarExistenciaECache2(String chaveControle) {
        try {
            // Tenta obter o cache existente
            CacheData cachedData = (CacheData) redisTemplate.opsForValue().get(chaveControle);
    
            if (cachedData != null) {
                logger.info("### Recuperando dados do Cache {}: ", cachedData.toString());
                // Cache já existe
                return cachedData;
            }
    
            // Cria novos dados de cache
            CacheData newData = new CacheData(chaveControle, UUID.randomUUID().toString());
    
            // Tenta definir o novo cache se não existir
            Boolean isSet = redisTemplate.opsForValue().setIfAbsent(chaveControle, newData);
    
            if (Boolean.TRUE.equals(isSet)) {
                logger.info("### Gerando novo cache: {} ", newData.toString());
                redisTemplate.expire(chaveControle, CACHE_EXPIRATION_TIME, TimeUnit.SECONDS);
                return newData;
            } else {
                // Se o cache foi setado por outra thread antes
                return (CacheData) redisTemplate.opsForValue().get(chaveControle);
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
