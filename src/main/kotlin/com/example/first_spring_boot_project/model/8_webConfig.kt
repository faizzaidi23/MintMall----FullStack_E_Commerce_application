package com.example.first_spring_boot_project.model

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/*
just saving the file is not enough
we need to tell spring boot  that the uploads folder should be publicly accessible over the web


creating a new config package and add a file
*/

@Configuration
class WebConfig: WebMvcConfigurer{
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:uploads/")
    }
}