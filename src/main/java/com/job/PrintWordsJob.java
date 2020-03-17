package com.job;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PrintWordsJob implements Job {

    /**
     * job实现类中添加setter方法对应JobDataMap中的key,Quartz框架默认的jobFactory实现类在初始化job实例对象时会指定调用这些setter()方法
     */
    private String message;
    public void setMessage(String message) {
        this.message = message;
    }
    /*public PrintWordsJob(){
        System.out.println("构造方法被调用。每次调度器执行job时，在调用execute()方法前会创建一个新的job实例，当调用完成，" +
                "关联的job实例会被释放，被垃圾回收机制处理");
    }*/


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        /**
         * JobExecutionContext:
         * JobExecutionContext中包含了Quartz运行时的环境以及Job本身的详细数据信息。
         * 当Schedule调度执行一个Job的时候，就会将JobExecutionContext传递给该Job的execute()中，
         * Job就可以通过JobExecutionContext对象访问到quartz运行时的环境和job本身的明细数据。
         */
        //获取JobDetail中的数据
        JobKey key = context.getJobDetail().getKey();
        System.out.println("工作任务的名称"+key.getName()+"----工作任务组"+key.getGroup());
        System.out.println("任务类名称："+context.getJobDetail().getJobClass().getName());
        System.out.println("任务类名称："+context.getJobDetail().getJobClass().getSimpleName());

        /**
         * 在进行任务调度时，JobDataMap存储在JobExecutionContext中，获取非常方便
         * JobDataMap实现了JDK的Map接口，可以以Key-Value的形式存储数据。
         * JobDetail、Trigger都可以使用JobDataMap来设置一些参数或信息，
         * Job执行execute()方法的时候，JobExecutionContext可以获取到JobExecutionContext中的信息
         *
         */
        //从JobDetail对象中获取JobDataMap的数据
       /* JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        System.out.println("任务数据的参数值："+jobDataMap.get("message"));*/

        //从Trigger对象中获取JobDataMap的数据
        /*JobDataMap triggerJobDataMap1 = context.getTrigger().getJobDataMap();
        System.out.println("触发器数据的参数值："+triggerJobDataMap1.get("message"));*/

        //获取Trigger触发器的内容
        TriggerKey triggerKey = context.getTrigger().getKey();
        System.out.println("触发器的名称"+triggerKey.getName()+"----触发器组"+triggerKey.getGroup());

        //其他内容
        System.out.println("当前任务的执行时间："+context.getFireTime());
        System.out.println("当前任务下一次的执行时间："+context.getNextFireTime());

        /**
         * 通过setter方法获取JobDataMap中的key=message的值
         * 注意，这里遇到同名的key，trigger中的.usingJobData("message","Trigger")会覆盖JobDetail中的 .usingJobData("message","JobDetail")
         */
        System.out.println("通过setter方法获取"+message);

        //任务逻辑
        String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
        System.out.println("PrintWordsJob start at:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100)+"\n");
    }


}
