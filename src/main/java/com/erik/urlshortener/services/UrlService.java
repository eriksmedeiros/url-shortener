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
        UrlModel url = new UrlModel(id, fullUrl, LocalDateTime.now().plusHours(1));

        return urlRepository.save(url);
    }

    // gera um ID único para cada URL
    // randomAlphanumeric gera uma string aleatória com letras maiúsculas, minúsculas e números
    private String generateUniqueId() {
        String id;
        do {
            id = RandomStringUtils.randomAlphanumeric(5);
        } while (urlRepository.existsById(id));
        return id;
    }

    public UrlModel findById(String id) {
        UrlModel url = urlRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        if (url.getExpiresAt() != null && url.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("URL has expired");
        }

        return url;
    }
}
