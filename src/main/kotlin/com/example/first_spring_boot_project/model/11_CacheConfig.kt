package com.example.first_spring_boot_project.model

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import java.time.Duration

//todo--> why we should configure caches
/*
By defining the caches in a central configuration we can:

Set Expiration policies (TTL)-->

We can specify how long the data should live in a cache
before it is automatically removed. this is crucial for preventing stale data.
For example, product data might be cached for 10 minutes, while a list of categories might be cached for 24 hours


Avoid Types-->

It ensures we can use same cache name consistently across the application

Control Resources--->
It gives us a clear overview of all the caches the application will be using



*/


@Configuration
class CacheConfig {

    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager {

        // This is the default configuration for any cache that is not explicitly configured
        val defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(5)) // Default expiration :5 minutes

        // This is the specific configuration for the products cache
        val productsCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10)) //Sets expiration for "products" cache to 10 minutes


        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(defaultConfig)
            .withCacheConfiguration("products",productsCacheConfig)
            .build()
    }
}