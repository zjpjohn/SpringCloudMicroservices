package com.cloud.config.remote.fallback;

import com.cloud.config.remote.ConfigServerService;
import com.cloud.config.vo.JsonData;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Project: ConfigServerAdmin
 * Module Desc:com.juntu.config.remote.fallback
 * User: zjprevenge
 * Date: 2016/11/25
 * Time: 17:26
 */
@Component
public class ConfigServerFallback implements ConfigServerService {
    @Override
    public JsonData refresh(@RequestParam(name = "destination", required = false) String destination) {
        return JsonData.failure();
    }
}
