package com.example.account.calculator.config

import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class RedisCacheConfig : CachingConfigurerSupport(), CachingConfigurer {

    @Bean
    override fun errorHandler(): CacheErrorHandler? {
        return RedisCacheErrorHandler()
    }
}