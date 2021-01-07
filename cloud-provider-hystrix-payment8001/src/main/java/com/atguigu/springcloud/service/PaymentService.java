package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String PaymentInfo_ok(Long id){
        return "线程池："+Thread.currentThread().getName()+"PaymentInfo_ok,id:"+id;
    }

    @HystrixCommand(fallbackMethod = "PaymentInfo_timeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String PaymentInfo_timeout(Long id){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int i = 1/0;
        return "线程池："+Thread.currentThread().getName()+"PaymentInfo_timeout,id:"+id;
    }

    public String PaymentInfo_timeoutHandler(Long id){
        return "线程池："+Thread.currentThread().getName()+"系统繁忙,请稍后再试!"+id;
    }
}
