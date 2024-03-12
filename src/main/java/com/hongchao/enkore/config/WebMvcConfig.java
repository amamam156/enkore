package com.hongchao.enkore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport
{
    // Set static resource mapping
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry){

        log.info("Start setting static resource mapping...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}
