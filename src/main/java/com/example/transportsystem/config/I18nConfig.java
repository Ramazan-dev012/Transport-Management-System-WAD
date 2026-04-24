package com.example.transportsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.time.Duration;
import java.util.Locale;

@Configuration
public class I18nConfig implements WebMvcConfigurer {

    /**
     * Stores selected locale in a cookie so it works across pages and without login.
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setCookieName("LOCALE");
        resolver.setDefaultLocale(Locale.forLanguageTag("ru"));
        resolver.setCookiePath("/");
        resolver.setCookieMaxAge((int) Duration.ofDays(365).getSeconds());
        resolver.setCookieHttpOnly(false);
        resolver.setCookieSameSite("Lax");
        return resolver;
    }

    /**
     * Switch locale via query param: ?lang=ru | kk | en
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
