package com.imooc.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by simon.
 */
@SpringBootApplication
public class RatelimiterApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(RatelimiterApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
