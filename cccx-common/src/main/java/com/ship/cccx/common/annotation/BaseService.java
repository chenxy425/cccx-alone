package com.ship.cccx.common.annotation;

import java.lang.annotation.*;

/**
 * 初始化继承BaseService的/service
 * Created by thinkam on 17-10-31.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseService {
}
