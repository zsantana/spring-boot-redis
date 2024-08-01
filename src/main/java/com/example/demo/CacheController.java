package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping(value ="v1/cache", produces = MediaType.APPLICATION_JSON_VALUE)
    public CacheData verificarEArmazenarEmCache(String key) {
        return  cacheService.verificarExistenciaECache(key);
    }

    @GetMapping(value ="v2/cache", produces = MediaType.APPLICATION_JSON_VALUE)
    public CacheData verificarEArmazenarEmCache2(String key) {
        return cacheService.verificarExistenciaECache2(key);
    }


    @GetMapping(value ="v3/cache", produces = MediaType.APPLICATION_JSON_VALUE)
    public CacheData verificarEArmazenarEmCache3(String key) {
        return cacheService.verificarExistenciaECache3(key);
    }
    
}
