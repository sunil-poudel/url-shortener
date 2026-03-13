package com.sunilpoudel.urlshortener.controller;

import com.sunilpoudel.urlshortener.dto.ShortenUrlRequest;
import com.sunilpoudel.urlshortener.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final UrlService urlService;

    public HomeController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/shorten")
    public String shorten(@RequestParam String originalUrl, Model model) {
        ShortenUrlRequest request = new ShortenUrlRequest();
        request.setOriginalUrl(originalUrl);

        var response = urlService.shortenUrl(request);
        model.addAttribute("shortUrl", response.getShortUrl());

        return "index";
    }
}