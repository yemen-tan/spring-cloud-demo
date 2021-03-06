package com.imooc.springcloud;

import feign.Feign;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by simon.
 */
@EnableDiscoveryClient
@SpringBootApplication
// 发起feign调用才需要加此注解
@EnableFeignClients
@EnableCircuitBreaker
public class HystrixFallbackApplication {

    public static void main(String[] args) throws NoSuchMethodException {
        new SpringApplicationBuilder(HystrixFallbackApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);

//        System.out.println(Feign.configKey(MyService.class,
//                MyService.class.getMethod("retry", int.class)));
    }

}
