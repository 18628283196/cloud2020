package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderHystrixController {

    @Resource
    private PaymentService paymentService;


    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    String PaymentInfo_ok(@PathVariable("id") Long id){
        return paymentService.PaymentInfo_ok(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    String PaymentInfo_timeout(@PathVariable("id") Long id){
        return paymentService.PaymentInfo_timeout(id);
    }

}
