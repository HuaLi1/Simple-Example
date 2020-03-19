package com.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@PersistJobDataAfterExecution//这个注解后，调用job时，不会重新创建job实例，会持久化job，数据不会重置
public class HelloJobSimpleTrigger implements Job {

    private int count;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String printTime = simpleDateFormat.format(new Date());
        count++;
        System.out.println("操作时间:" + printTime + ", prints: Hello Job-" + count+"\n");
        context.getJobDetail().getJobDataMap().put("count",count);

    }

    public void setCount(int count) {
        this.count = count;
    }
}
