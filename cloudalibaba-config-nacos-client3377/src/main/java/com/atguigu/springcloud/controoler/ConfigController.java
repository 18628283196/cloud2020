package com.atguigu.springcloud.controoler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //支持nacos动态刷新功能
public class ConfigController {
    @Value(value = "${config.info}")
    private String configInfo;

    @GetMapping("/config/info")
    public String getConfig(){
        return configInfo;
    }
}
