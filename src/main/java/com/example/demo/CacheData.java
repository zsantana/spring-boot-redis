package com.example.demo;

import java.io.Serializable;


public record CacheData(String sessao, String ticket) implements Serializable {
}

