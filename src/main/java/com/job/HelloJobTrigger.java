package com.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class HelloJobTrigger implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取jobKey，startTime，endTime
        Trigger trigger = context.getTrigger();
        System.out.println("jobKey名称："+trigger.getJobKey().getName()+"    jobKey组名："+trigger.getJobKey().getGroup());

        System.out.println("任务开始时间:"+simpleDateFormat.format(trigger.getStartTime()));
        System.out.println("任务结束时间:"+simpleDateFormat.format(trigger.getEndTime()));
        //任务逻辑

        String printTime = simpleDateFormat.format(new Date());
        System.out.println("操作时间:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100)+"\n");



    }


}
