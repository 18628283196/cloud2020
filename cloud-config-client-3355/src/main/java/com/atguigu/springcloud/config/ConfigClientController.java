package com.atguigu.springcloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {
    @Value(value = "${config.info}")
    private String configInfo;

    @GetMapping("configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}