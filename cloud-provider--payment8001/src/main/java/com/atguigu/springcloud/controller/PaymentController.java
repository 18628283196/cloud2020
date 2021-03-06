package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    @ResponseBody
    public CommResult create(@RequestBody Payment payment){
        try {
            int result = paymentService.create(payment);
            if (result > 0){
                return new CommResult(200,"成功,serverPort:"+serverPort,result);
            }else {
                return new CommResult(400,"失败",result);
            }
        }catch (Exception e){
            logger.error(e.toString());
            return new CommResult(500,"服务器异常");
        }
    }

    @GetMapping(value = "/payment/query/{id}")
    @ResponseBody
    public CommResult create(@PathVariable("id") Long id){
        try {
            Payment payment = paymentService.getPaymentById(id);
            if (payment != null){
                return new CommResult(200,"成功,serverPort:"+serverPort,payment);
            }else {
                return new CommResult(400,"失败",new Payment());
            }
        }catch (Exception e){
            logger.error(e.toString());
            return new CommResult(500,"服务器异常",new Payment());
        }
    }

    @GetMapping("/payment/lb")
    @ResponseBody
    public String getPaymentlb(){
        return serverPort;
    }


    @GetMapping(value = "/payment/feign/timeout")
    @ResponseBody
    public String timeout(){
        try {
            Thread.sleep(3000);
        }catch (Exception e){
            logger.error(e.toString());
        }

        return serverPort;
    }

    @GetMapping(value = "/payment/zipkin")
    @ResponseBody
    public String zipkin(){
        return "hi I'm paymentzipkin";
    }
}
