package com.imooc.springcloud.annotation;

import java.lang.annotation.*;

/**
 * Created by simon.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimiter {

    int limit();

    String methodKey() default "";

}
