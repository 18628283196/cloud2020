package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.MyLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //private static final String PAYMENT_URL = "http://localhost:8001"; //单机版
    private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private MyLoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create", payment, CommResult.class);
    }

    //getForObject:返回json数据
    @GetMapping("/consumer/payment/query/{id}")
    public CommResult<Payment> getPayment(@PathVariable("id")Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/query/"+id, CommResult.class);
    }

    //getForEntity:返回ResponseEntity对象，包含了响应中的一些信息，比如响应头、响应体、响应状态
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommResult<Payment> getForEntity(@PathVariable("id")Long id){
        ResponseEntity<CommResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/query/" + id, CommResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else {
            return new CommResult<>(444,"操作失败");
        }

    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentlb(){

        List<ServiceInstance> list = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        if (list == null || list.size() <= 0){
            return null;
        }

        ServiceInstance instance = loadBalancer.instance(list);

        URI uri = instance.getUri();

        return restTemplate.getForObject(uri+"/payment/lb", String.class);

    }

}
