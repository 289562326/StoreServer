package com.znv.framework.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 分布式定时任务
 * @author MHm
 * @date 2018/12/27.
 */
//@PersistJobDataAfterExecution
//@DisallowConcurrentExecution
@Component
@ScheduledJob(name = "JobA", cronExp = "*/10 * * * * ?")
@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub
        System.out.println("\nQuartz job " + new Date());
    }
}
