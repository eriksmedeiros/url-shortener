package com.erik.urlshortener.dto;

public record Response(String shortUrl, String fullUrl) {
    public Response(String shortUrl, String fullUrl) {
        this.shortUrl = shortUrl;
        this.fullUrl = fullUrl;
    }
}