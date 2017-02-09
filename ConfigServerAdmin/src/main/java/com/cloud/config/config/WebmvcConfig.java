package com.cloud.config.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Project: ConfigServerApp
 * Module Desc:com.juntu.config.config
 * User: zjprevenge
 * Date: 2016/11/24
 * Time: 17:01
 */
//@Configuration
public class WebmvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/css/**",
                "classpath:/static/css/patterns",
                "classpath:/static/css/plugins/**/**",
                "classpath:/static/img/**",
                "classpath:/static/fonts/**",
                "classpath:/static/js/**",
                "classpath:/static/js/plugins/**/**",
                "classpath:/static/plugins.fullavatareditor.scripts/**");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
