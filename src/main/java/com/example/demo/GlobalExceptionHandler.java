package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import io.lettuce.core.RedisCommandTimeoutException;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import redis.clients.jedis.exceptions.JedisConnectionException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<ProblemDetail> handleRedisConnectionFailureException(RedisConnectionFailureException ex, WebRequest request) {
        logger.error("### Erro (handleRedisConnectionFailureException) ao conectar REDIS: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Erro de conexão com Redis");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }

    @ExceptionHandler(RedisCommandTimeoutException.class)
    public ResponseEntity<ProblemDetail> handleRedisCommandTimeoutException(RedisCommandTimeoutException ex, WebRequest request) {
        logger.error("### Erro handleRedisCommandTimeoutException {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Timeout de comando Redis");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }

    @ExceptionHandler(RedisSystemException.class)
    public ResponseEntity<ProblemDetail> handleRedisSystemException(RedisSystemException ex, WebRequest request) {
        logger.error("### Erro handleRedisSystemException {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Erro de Sistema Redis");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }

    @ExceptionHandler(JedisConnectionException.class)
    public ResponseEntity<ProblemDetail> handleJedisConnectionException(JedisConnectionException ex, WebRequest request) {
        logger.error("### Erro handleJedisConnectionException {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Erro ao conectar o Redis");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }


    @ExceptionHandler(RedisOperationException.class)
    public ResponseEntity<ProblemDetail> handleRedisConnectionFailureException(RedisOperationException ex, WebRequest request) {
        logger.error("### Erro ao conectar REDIS: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Erro de conexão com Redis");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }
}
