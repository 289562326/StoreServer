package com.znv.framework.task;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式定时任务
 * @author MaHuiming
 * @date 2018/12/27.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScheduledJob {
    String name();

    String group() default "DEFAULT_GROUP";

    String cronExp();
}
