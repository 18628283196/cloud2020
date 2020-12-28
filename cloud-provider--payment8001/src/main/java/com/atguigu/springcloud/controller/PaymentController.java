package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@Slf4j
public class PaymentController {

    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    @ResponseBody
    public CommResult create(@RequestBody Payment payment){
        try {
            int result = paymentService.create(payment);
            if (result > 0){
                return new CommResult(200,"成功",result);
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
                return new CommResult(200,"成功",payment);
            }else {
                return new CommResult(400,"失败",new Payment());
            }
        }catch (Exception e){
            logger.error(e.toString());
            return new CommResult(500,"服务器异常",new Payment());
        }
    }
}
