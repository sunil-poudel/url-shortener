package com.sunilpoudel.urlshortener.service;

import com.sunilpoudel.urlshortener.dto.ShortenUrlRequest;
import com.sunilpoudel.urlshortener.dto.ShortenUrlResponse;
import com.sunilpoudel.urlshortener.entity.ShortUrl;
import com.sunilpoudel.urlshortener.repo.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UrlService {

    private final ShortUrlRepository shortUrlRepository;
    @Value("${app.base-url}")
    private String baseUrl;

    public UrlService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    public ShortenUrlResponse shortenUrl(ShortenUrlRequest request) {
        String originalUrl = request.getOriginalUrl();

        if (originalUrl == null || originalUrl.isBlank()) {
            throw new RuntimeException("URL cannot be empty");
        }
        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "https://" + originalUrl;
        }

        String shortCode = generateUniqueCode();

        ShortUrl shortUrl = new ShortUrl(originalUrl, shortCode);
        shortUrlRepository.save(shortUrl);

        String shortLink = baseUrl + shortCode;

        return new ShortenUrlResponse(originalUrl, shortCode, shortLink);
    }

    public String getOriginalUrl(String code) {
        ShortUrl shortUrl = shortUrlRepository.findByShortCode(code)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        shortUrlRepository.save(shortUrl);

        return shortUrl.getOriginalUrl();
    }

    public List<ShortUrl> getAllUrls() {
        return shortUrlRepository.findAll();
    }

    private String generateUniqueCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        String code;

        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            code = sb.toString();
        } while (shortUrlRepository.existsByShortCode(code));

        return code;
    }
}