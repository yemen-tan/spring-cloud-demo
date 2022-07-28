package com.imooc.springcloud;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Created by simon
 * guava实现单机版客户端限流-令牌桶算法
 * 单机半客户端限流的适用场景：单机资源敏感
 * 单机半客户端限流的缺点：不是分布式限流，无法对数据库、缓存中间件等公用资源进行限流
 */
@RestController
@Slf4j
public class Controller {

    // 每秒钟产生2个令牌
    RateLimiter limiter = RateLimiter.create(2.0);



    // 非阻塞限流
    @GetMapping("/tryAcquire")
    public String tryAcquire(Integer count) {

        // 每个请求消耗count个令牌
        if (limiter.tryAcquire(count)) {
            log.info("success, rate is {}", limiter.getRate());
            return "success";
        } else {
            log.info("fail, rate is {}", limiter.getRate());
            return "fail";
        }
    }

    // 限定时间的非阻塞限流
    @GetMapping("/tryAcquireWithTimeout")
    public String tryAcquireWithTimeout(Integer count, Integer timeout) {
        /*
            1.带有阻塞时间，timeout时间内没有获取到令牌会走else逻辑
            2.tryAcquire自带快速失败机制，当闯传入的count很大时，在timeout时间内不可能
              产生那么多令牌是不会阻塞指定的时间而是马上返回失败
         */
        if (limiter.tryAcquire(count, timeout, TimeUnit.SECONDS)) {
            log.info("success, rate is {}", limiter.getRate());
            return "success";
        } else {
            log.info("fail, rate is {}", limiter.getRate());
            return "fail";
        }
    }

    // 同步阻塞限流，请求一直阻塞直到获取到令牌
    @GetMapping("/acquire")
    public String acquire(Integer count) {
        limiter.acquire(count);
        log.info("success, rate is {}", limiter.getRate());
        return "success";
    }


    // Nginx专用
    // 1. 修改host文件 -> www.imooc-training.com = localhost 127.0.0.1
    //    (127.0.0.1	www.imooc-training.com)
    // 2. 修改nginx -> 将步骤1中的域名，添加到路由规则当中
    //    配置文件地址： /usr/local/nginx/conf/nginx.conf
    // 3. 添加配置项：参考resources文件夹下面的nginx.conf
    //
    // 重新加载nginx(Nginx处于启动) => sudo /usr/local/nginx/sbin/nginx -s reload
    @GetMapping("/nginx")
    public String nginx() {
        log.info("Nginx success");
        return "success";
    }

    @GetMapping("/nginx-conn")
    public String nginxConn(@RequestParam(defaultValue = "0") int secs) {
        try {
            Thread.sleep(1000 * secs);
        } catch (Exception e) {
        }
        return "success";
    }

}
