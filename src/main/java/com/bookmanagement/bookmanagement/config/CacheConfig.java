package com.bookmanagement.bookmanagement.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // 전체 도서 목록
        cacheManager.registerCustomCache("books", Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build());

        // 개별 도서
        cacheManager.registerCustomCache("book", Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build());

        // 도서 검색
        cacheManager.registerCustomCache("searchBooks", Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(500)
                .build());

        // 태그 기반 필터링
        cacheManager.registerCustomCache("filterBooksByTags", Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(500)
                .build());

        // 전체 사용자 목록
        cacheManager.registerCustomCache("users", Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build());

        // 개별 사용자
        cacheManager.registerCustomCache("user", Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build());

        // 도서 대출 상태 조회
        cacheManager.registerCustomCache("loanStatus", Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(500)
                .build());

        return cacheManager;
    }
}