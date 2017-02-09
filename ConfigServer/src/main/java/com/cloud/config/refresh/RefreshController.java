package com.cloud.config.refresh;

import com.cloud.config.domain.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.endpoint.RefreshBusEndpoint;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Project: ConfigServer
 * Module Desc:com.juntu.config.refresh
 * User: zjprevenge
 * Date: 2016/11/24
 * Time: 18:08
 */
@RestController
public class RefreshController {

    private static final Logger log = LoggerFactory.getLogger(RefreshController.class);
    @Resource
    private RefreshBusEndpoint refreshBusEndpoint;

    @RequestMapping(value = "/server/refresh", method = RequestMethod.GET)
    public JsonData refresh(@RequestParam(name = "destination",required = false) String destination) {
        log.info("刷新配置...");
        try {
            if (StringUtils.isNotBlank(destination)) {
                refreshBusEndpoint.refresh(destination);
            } else {
                refreshBusEndpoint.refresh(null);
            }
            return JsonData.success();
        } catch (Exception e) {
            log.error("refresh config error:{}", e);
            return JsonData.failure();
        }
    }
}
