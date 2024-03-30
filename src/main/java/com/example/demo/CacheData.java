package com.example.demo;

import java.io.Serializable;

public class CacheData implements Serializable {

    private String sessao;
    private String ticket;

    public CacheData() {
    }

    public String getSessao() {
        return sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public CacheData(String sessao, String ticket) {
        this.sessao = sessao;
        this.ticket = ticket;
    }

    // Getters e setters
}
