package com.example.transportsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Helper redirects for language switching.
 */
@Controller
public class LocaleRedirectController {

    @GetMapping("/lang")
    public String changeLang(@RequestParam("lang") String lang, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String fallback = "/transport";

        String basePath;
        try {
            if (referer == null || referer.isBlank()) {
                basePath = fallback;
            } else {
                // Keep only path+query from Referer, drop scheme/host.
                URI uri = URI.create(referer);
                String path = (uri.getPath() == null || uri.getPath().isBlank()) ? fallback : uri.getPath();
                String query = uri.getQuery();
                basePath = (query == null || query.isBlank()) ? path : path + "?" + query;
            }
        } catch (IllegalArgumentException ex) {
            basePath = fallback;
        }

        String target = UriComponentsBuilder.fromUriString(basePath)
                .replaceQueryParam("lang", lang)
                .build()
                .toUriString();

        return "redirect:" + target;
    }
}
