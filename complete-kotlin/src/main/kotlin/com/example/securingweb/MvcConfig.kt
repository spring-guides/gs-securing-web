package com.example.securingweb

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfig : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) = with(registry) {
        addViewController("/home").setViewName("home")
        addViewController("/").setViewName("home")
        addViewController("/hello").setViewName("hello")
        addViewController("/login").setViewName("login")
    }
}
