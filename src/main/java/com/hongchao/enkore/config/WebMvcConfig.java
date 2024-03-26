package com.hongchao.enkore.config;

import com.hongchao.enkore.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport
{
    // Set static resource mapping
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry)
    {

        log.info("Start setting static resource mapping...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    // extent MVC format message to Message Converters
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        log.info("Extent Message Converter...");
        // create object
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // switch jackson to json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // add to message converter
        converters.add(0, messageConverter);

    }
}
