package com.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class MyScheduler {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        /**
         * JobDetail绑定指定的Job，每次Scheduler调度执行一个Job的时候，首先会拿到对应的Job，然后创建该Job实例，再去执行Job中
         * 的execute()的内容，任务执行结束后，关联的Job对象实例会被释放，且会被JVM GC清除。
         * JobDetail的重要属性：name(必须指定)，group(默认DEFAULT),jobClass，jonDataMap
         */
        JobDetail jobDetail = JobBuilder.newJob(PrintWordsJob.class)
                .withIdentity("job1", "group1")
                .usingJobData("message","JobDetail")
                .build();
        System.out.println("名称："+jobDetail.getKey().getName()+
                "\n组名："+jobDetail.getKey().getGroup()+
                "\n任务类名："+jobDetail.getJobClass().getName());
        // 3、构建Trigger实例,每隔2s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2)//每隔2s执行一次
                        .repeatForever())
                .usingJobData("message","Trigger")
                .build();//一直执行

        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------scheduler start ! ------------");
        scheduler.start();

        //睡眠
        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");


    }

}
