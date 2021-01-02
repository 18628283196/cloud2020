package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLoadBalancer implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAtomicInteger(){
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next  = current > Integer.MAX_VALUE ? 0 : current +1;
        }while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("第几次访问,次数next:"+next);
        return next;
    }

    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {
        int index = getAtomicInteger() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
