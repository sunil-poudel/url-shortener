package com.sunilpoudel.urlshortener.dto;

public class ShortenUrlRequest {

    private String originalUrl;

    public ShortenUrlRequest() {
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}