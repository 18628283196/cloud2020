package com.atguigu.springcloud.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String PaymentInfo_ok(Long id){
        return "线程池："+Thread.currentThread().getName()+"PaymentInfo_ok,id:"+id;
    }

    public String PaymentInfo_timeout(Long id){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+"PaymentInfo_timeout,id:"+id;
    }
}
