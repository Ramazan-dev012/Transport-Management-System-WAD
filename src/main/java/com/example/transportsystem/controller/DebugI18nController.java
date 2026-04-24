package com.example.transportsystem.controller;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RestController
public class DebugI18nController {

    private final MessageSource messageSource;

    public DebugI18nController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/debug/i18n")
    public Map<String, String> debugI18n(
            @RequestParam(defaultValue = "ru") String lang,
            HttpServletRequest request
    ) {
        Locale locale = Locale.forLanguageTag(lang);
        Map<String, String> out = new LinkedHashMap<>();

        out.put("requestedLang", lang);
        out.put("localeTag", locale.toLanguageTag());
        out.put("requestLocale", request.getLocale() != null ? request.getLocale().toLanguageTag() : "<null>");
        out.put("cookie.LOCALE", readLocaleCookie(request));

        out.put("nav.main", msg("nav.main", locale));
        out.put("nav.buses", msg("nav.buses", locale));
        out.put("nav.passengers", msg("nav.passengers", locale));
        out.put("nav.tickets", msg("nav.tickets", locale));
        out.put("nav.login", msg("nav.login", locale));
        out.put("nav.register", msg("nav.register", locale));
        out.put("nav.logout", msg("nav.logout", locale));
        out.put("main.hero.subtitle", msg("main.hero.subtitle", locale));

        return out;
    }

    private String readLocaleCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return "<none>";
        for (Cookie c : cookies) {
            if ("LOCALE".equals(c.getName())) {
                return c.getValue();
            }
        }
        return "<none>";
    }

    private String msg(String code, Locale locale) {
        return messageSource.getMessage(code, null, "<missing:" + code + ">", locale);
    }
}
