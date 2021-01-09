package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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


    //=======================服务熔断==============

    @HystrixCommand(fallbackMethod = "PaymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
    })
    public String PaymentCircuitBreaker(@PathVariable("id")Long id){
        if (id < 0){
            throw  new RuntimeException("id不能为负数");
        }
        String serialNum = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "调用成功,流水号："+serialNum;
    }

    public String PaymentCircuitBreaker_fallback(@PathVariable("id")Long id){
        return "id不能为负数,请稍后再试!id:"+id;
    }
}
