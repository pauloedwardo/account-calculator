package com.example.account.calculator.config

import mu.KotlinLogging
import org.springframework.cache.Cache
import org.springframework.cache.interceptor.CacheErrorHandler
import java.lang.RuntimeException

private val logger = KotlinLogging.logger {}

class RedisCacheErrorHandler: CacheErrorHandler {
    override fun handleCacheGetError(exception: RuntimeException, cache: Cache, key: Any) {
        logger.info("Unable to get from cache ${cache.name} : ${exception.message}")
    }

    override fun handleCachePutError(exception: RuntimeException, cache: Cache, key: Any, value: Any?) {
        logger.info("Unable to put from cache ${cache.name} : ${exception.message}")
    }

    override fun handleCacheEvictError(exception: RuntimeException, cache: Cache, key: Any) {
        logger.info("Unable to evic from cache ${cache.name} : ${exception.message}")
    }

    override fun handleCacheClearError(exception: RuntimeException, cache: Cache) {
        logger.info("Unable to clear from cache ${cache.name} : ${exception.message}")
    }
}