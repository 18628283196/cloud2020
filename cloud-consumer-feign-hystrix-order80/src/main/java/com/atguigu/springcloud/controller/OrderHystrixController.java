package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_globle_fallbackmethod")
public class OrderHystrixController {

    @Resource
    private PaymentService paymentService;


    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    String PaymentInfo_ok(@PathVariable("id") Long id){
        return paymentService.PaymentInfo_ok(id);
    }

    /*@HystrixCommand(fallbackMethod = "PaymentInfo_timeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })*/
    @HystrixCommand //没配置具体的降级的方法，会走全局配置的降级方法
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    String PaymentInfo_timeout(@PathVariable("id") Long id){
        //int i = 1/0;
        return paymentService.PaymentInfo_timeout(id);
    }

    public String PaymentInfo_timeoutHandler(@PathVariable("id") Long id){
        return "我是消费者80,对方支付系统繁忙请10s后再试!";
    }

    public String payment_globle_fallbackmethod(){
        return "Globle异常处理信息,请稍后再试!";
    }

}
