package com.znv.framework.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 普通定时任务
 * @author MaHuiming
 * @date 2018/12/13.
 */
@Component
@Slf4j
public class HeartbeatTask {

    @Scheduled(initialDelay = 10000,fixedRate = 50000)
    @Async
    public void heartbeat(){
        log.info("heart beat....");
    }

}
