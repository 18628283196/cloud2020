package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.PaymentService;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String PaymentInfo_ok(Long id) {
        return "----PaymentInfo_ok------";
    }

    @Override
    public String PaymentInfo_timeout(Long id) {
        return "----PaymentInfo_timeout----";
    }
}
