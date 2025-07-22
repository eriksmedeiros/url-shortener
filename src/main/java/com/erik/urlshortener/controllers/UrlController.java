package com.erik.urlshortener.controllers;

import com.erik.urlshortener.dto.Request;
import com.erik.urlshortener.dto.Response;
import com.erik.urlshortener.models.UrlModel;
import com.erik.urlshortener.services.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/shorten-url")
    public String shortenUrl(@ModelAttribute Request request,
                             HttpServletRequest servletRequest,
                             Model model) {
        String fullUrl = request.url();
        UrlModel url = urlService.createShortUrl(fullUrl);

        // Obtem a URL da requisição e altera o caminho (URI) para incluir o ID da URL encurtada
        String redirectUrl = servletRequest.getRequestURL().toString().replace(servletRequest.getRequestURI(), "");
        String shortUrl = redirectUrl + "/" + url.getId();
        model.addAttribute("shortUrl", shortUrl);
        model.addAttribute("fullUrl", fullUrl);

        return "home";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") String id) {
        UrlModel urlModel = urlService.findById(id);
        // Redireciona para a URL completa
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(urlModel.getFullUrl()))
                .build();
    }

}
