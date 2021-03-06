package com.atguigu.springcloud.service;

import com.atguigu.springcloud.service.impl.PaymentServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentServiceImpl.class)
public interface PaymentService {

    @GetMapping("/payment/hystrix/ok/{id}")
    String PaymentInfo_ok(@PathVariable("id") Long id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    String PaymentInfo_timeout(@PathVariable("id") Long id);
}
