package com.cloud.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Project: ConfigClient
 * Module Desc:com.juntu.config
 * User: zjprevenge
 * Date: 2016/11/22
 * Time: 10:57
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigClientApplication.class)
                .web(true)
                .run(args);
    }
}
