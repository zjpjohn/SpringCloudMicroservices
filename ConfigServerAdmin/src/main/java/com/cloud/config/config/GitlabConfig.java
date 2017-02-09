package com.cloud.config.config;

import com.messners.gitlab.api.GitLabApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * Project: ConfigServer
 * Module Desc:com.juntu.config.config
 * User: zjprevenge
 * Date: 2016/11/21
 * Time: 14:56
 */
@Configuration
public class GitlabConfig {

    @Resource
    private Environment env;

    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi(env.getProperty("gitlab.host"), env.getProperty("gitlab.token"));
    }
}
