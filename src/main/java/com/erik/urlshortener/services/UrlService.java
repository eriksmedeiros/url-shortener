package com.erik.urlshortener.services;

import com.erik.urlshortener.models.UrlModel;
import com.erik.urlshortener.repositorys.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlModel createShortUrl(String fullUrl) {
        String id = generateUniqueId();
        UrlModel url = new UrlModel(id, fullUrl, LocalDateTime.now());

        return urlRepository.save(url);
    }

    private String generateUniqueId() {
        String id;
        do {
            id = RandomStringUtils.randomAlphanumeric(5);
        } while (urlRepository.existsById(id));
        return id;
    }

    public UrlModel findById(String id) {
        return urlRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("URL not found"));
    }
}
