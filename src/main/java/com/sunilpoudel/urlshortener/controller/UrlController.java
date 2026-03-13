package com.sunilpoudel.urlshortener.controller;

import com.sunilpoudel.urlshortener.dto.ShortenUrlRequest;
import com.sunilpoudel.urlshortener.dto.ShortenUrlResponse;
import com.sunilpoudel.urlshortener.entity.ShortUrl;
import com.sunilpoudel.urlshortener.service.UrlService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ShortenUrlResponse shortenUrl(@RequestBody ShortenUrlRequest request) {
        return urlService.shortenUrl(request);
    }

    @GetMapping
    public List<ShortUrl> getAllUrls() {
        return urlService.getAllUrls();
    }
}