package com.cloud.config.remote;

import com.cloud.config.remote.fallback.ConfigServerFallback;
import com.cloud.config.vo.JsonData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Project: ConfigServerAdmin
 * Module Desc:com.juntu.config.remote
 * User: zjprevenge
 * Date: 2016/11/24
 * Time: 17:30
 */
@FeignClient(value = "config-server", fallback = ConfigServerFallback.class)
public interface ConfigServerService {

    /**
     * 刷新配置信息
     *
     * @param destination 指定服务的配置信息
     */
    @RequestMapping(value = "/server/refresh", method = RequestMethod.GET)
    JsonData refresh(@RequestParam(name = "destination", required = false) String destination);

}
