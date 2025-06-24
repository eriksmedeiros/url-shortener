package com.erik.urlshortener.controllers;

import com.erik.urlshortener.dto.ShortenUrlRequestDto;
import com.erik.urlshortener.dto.ShortenUrlResponseUrl;
import com.erik.urlshortener.models.UrlModel;
import com.erik.urlshortener.services.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten-url")
    public ResponseEntity<?> shortenUrl(@RequestBody ShortenUrlRequestDto shortenUrlRequestDto,
                                                    HttpServletRequest servletRequest) {
        String fullUrl = shortenUrlRequestDto.url();
        UrlModel url = urlService.createShortUrl(fullUrl);

        String redirectUrl = servletRequest.getRequestURL().toString().replace(servletRequest.getRequestURI(), "");
        String shortUrl = redirectUrl + "/" + url.getId();

        return ResponseEntity.ok(new ShortenUrlResponseUrl(shortUrl));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String id) {
        UrlModel urlModel = urlService.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(urlModel.getFullUrl()))
                .build();
    }

}
