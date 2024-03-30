package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("v1/cache")
    public CacheData verificarEArmazenarEmCache(String key) {
        return  cacheService.verificarExistenciaECache(key);
    }

    @GetMapping("v2/cache")
    public CacheData verificarEArmazenarEmCache2(String key) {
        return cacheService.verificarExistenciaECache2(key);
    }


    @GetMapping("v3/cache")
    public CacheData verificarEArmazenarEmCache3(String key) {
        return cacheService.verificarExistenciaECache3(key);
    }
    
}
