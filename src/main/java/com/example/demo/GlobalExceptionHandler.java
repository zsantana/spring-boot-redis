package com.example.demo;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.lettuce.core.RedisCommandTimeoutException;
import redis.clients.jedis.exceptions.JedisConnectionException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<String> handleRedisConnectionFailureException(RedisConnectionFailureException ex) {
        return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro de conexão com Redis: " + ex.getMessage());
    }

    @ExceptionHandler(RedisCommandTimeoutException.class)
    public ResponseEntity<String> handleRedisCommandTimeoutException(RedisCommandTimeoutException ex) {
        return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Timeout de comando Redis: " + ex.getMessage());
    }


    @ExceptionHandler(RedisSystemException.class)
    public ResponseEntity<String> handleRedisSystemException(RedisSystemException ex) {
        return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Timeout de comando Redis V2: " + ex.getMessage());
    }


    @ExceptionHandler(JedisConnectionException.class)
    public ResponseEntity<String> handleJedisConnectionException(JedisConnectionException ex) {
        return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao conectar o Redis: " + ex.getMessage());
    }

    

    
    
}
