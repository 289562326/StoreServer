package com.znv.peim.test;

import com.znv.framework.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.Executors.newScheduledThreadPool;

/**
 * @author MaHuiming
 * @date 2018/12/8.
 */
@Slf4j
@Component
@Service
public class RedisCapability {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    private String test_string_key = "111";
    private String test_string_value = "111";
    private String read_thread_name = "redis===";
    volatile boolean runState = true;

    // 开启时间
    long startTime = 0;

    long endTime = 0;

    // 工作线程
    Thread[] workers = null;

    // 线程数量
    int threadNumber = 0;

    int redis_write_count = 10;

    AtomicLong writeCount = new AtomicLong(redis_write_count);

    // 测试完成判断
    public Semaphore semaphore = new Semaphore(1);

    // 定时器
    ScheduledExecutorService executorService = newScheduledThreadPool(1);

    public void init() {
        // 默认线程数量为硬件内核数的2倍
        this.threadNumber = 4;
        workers = new Thread[threadNumber];
        for (int i = 0; i < threadNumber; i++) {
            redisUtil.set("1","1");
            workers[i] = new Thread(new RedisWriteTask(redisTemplate));
            workers[i].setDaemon(true);
            workers[i].setName(read_thread_name + "i");
        }

        executorService.scheduleAtFixedRate(new PrintTimer(), 2, 15, TimeUnit.SECONDS);

    }

    /**
     * 启动工作线程
     *
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        for (int i = 0; i < threadNumber; i++) {
            workers[i].start();
        }
        startTime = System.currentTimeMillis();

    }

    /**
     * 关闭线程
     */
    public void shutdown() {
        runState = false;
        executorService.shutdown();
    }

    class PrintTimer implements Runnable {
        @Override
        public void run() {
            // 结束
            if (runState == false) {
                executorService.shutdown();
            }
        }

    }

    public synchronized void log() {

        if (endTime == 0) {

            endTime = System.currentTimeMillis();

        } else {
            return;
        }

        StringBuilder sb = new StringBuilder();

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        long _count = redis_write_count;

        long useTime = endTime - startTime;
        long throughput = ((_count * 1000) / useTime);
        int strLength = test_string_value.getBytes().length;

        sb.append("\n======================================================\n");
        sb.append(
        "---------开始时间--------------结束时间-------------插入条数-----使用时间（毫秒）-----吞吐量--------测试运行线程数量--------单客户端并发量-------每个消息的大小\n");
        sb.append("-");
        sb.append(format.format(new Date(startTime)));
        sb.append("---");
        sb.append(format.format(new Date(endTime)));
        sb.append("------");
        sb.append(_count);
        sb.append("------");
        sb.append(useTime);
        sb.append("------");
        sb.append(throughput);
        sb.append("-----------");
        sb.append(threadNumber);
        sb.append("-----------");
        sb.append(throughput / threadNumber);
        sb.append("--------");
        sb.append(strLength);
        sb.append("byte-----");
        log.error(sb.toString());
        log.error("\n");
        semaphore.release();
    }

    class RedisWriteTask implements Runnable {
        private RedisTemplate redisTemplate;

        RedisWriteTask(RedisTemplate redisTemplate) {
            this.redisTemplate = redisTemplate;
        }

        @Override
        public void run() {
            semaphore.tryAcquire();
            while (runState) {
                try {
                    long number = writeCount.decrementAndGet();
                    if (number < 0) {
                        runState = false;
                        log();
                        break;
                    }
                    String writeKey = test_string_key + number;
                    redisTemplate.opsForValue().set(writeKey, test_string_value);
                } catch (Throwable t) {
                    // 连接失败
                    log.error("insert redis error:{}", t);
                }
            }
        }
    }

}
