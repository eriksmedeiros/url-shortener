package com.erik.urlshortener.controllers;

import com.erik.urlshortener.dto.Request;
import com.erik.urlshortener.dto.Response;
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
    public ResponseEntity<?> shortenUrl(@RequestBody Request request,
                                                    HttpServletRequest servletRequest) {
        String fullUrl = request.url();
        UrlModel url = urlService.createShortUrl(fullUrl);

        String redirectUrl = servletRequest.getRequestURL().toString().replace(servletRequest.getRequestURI(), "");
        String shortUrl = redirectUrl + "/" + url.getId();

        return ResponseEntity.ok(new Response(shortUrl));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") String id) {
        UrlModel urlModel = urlService.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(urlModel.getFullUrl()))
                .build();
    }

}
