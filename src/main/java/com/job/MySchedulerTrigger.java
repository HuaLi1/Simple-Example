package com.job;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MySchedulerTrigger {
    public static void main(String[] args) throws SchedulerException, InterruptedException {

        //任务开始时间
        Date startTime = new Date();
        startTime.setTime(startTime.getTime()+3000);
        //任务结束时间
        Date endTime = new Date();
        endTime.setTime(endTime.getTime()+8000);
        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        /**
         * JobDetail绑定指定的Job，每次Scheduler调度执行一个Job的时候，首先会拿到对应的Job，然后创建该Job实例，再去执行Job中
         * 的execute()的内容，任务执行结束后，关联的Job对象实例会被释放，且会被JVM GC清除。
         * JobDetail的重要属性：name(必须指定)，group(默认DEFAULT),jobClass，jonDataMap
         */
        JobDetail jobDetail = JobBuilder.newJob(HelloJobTrigger.class)
                .withIdentity("job1", "group1")
                .usingJobData("message","JobDetail")
                .build();

        // 3、构建Trigger实例,每隔2s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerA ", "triggerGroupA")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                //.startNow()//立即生效
                .startAt(startTime)
                .endAt(endTime)
                .usingJobData("message","Trigger")
                .build();//一直执行

        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("-------scheduler start ! ------------");
        scheduler.start();

        //睡眠
        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");

    }

}
