package com.rocketmq.producer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

//@Component
@Service
public class Producer {
    private static final Log LOG = LogFactory.getLog(Producer.class);
    /**
     * 生产者的组名
     */
    @Value("${rocketMq.producerGroup}")
    private String producerGroup;

    /**
     * NameServer 地址
     */
    @Value("${rocketMq.NAME_SERVER}")
    private String namesrvAddr;

    private List<String> mesList;
    private DefaultMQProducer producer;


    @PostConstruct    //@PostContruct是spring框架的注解，在方法上加该注解会在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
    public void initProducer(){
        //生产者的组名
        producer = new DefaultMQProducer(producerGroup);
        //指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(namesrvAddr);

        try {
            /**
             * Producer对象在使用之前必须要调用start初始化，初始化一次即可
             * 注意：切记不可以在每次发送消息时，都调用start方法
             */
            producer.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //发布消息
    public void sendMsg() {

        try {
            /**
             * Producer对象在使用之前必须要调用start初始化，初始化一次即可
             * 注意：切记不可以在每次发送消息时，都调用start方法
             */
            //producer.start();


            StopWatch stop = new StopWatch();
            stop.start();

            for (int i = 0; i < 5; i++) {
                //创建一个消息实例，包含 topic、tag 和 消息体
                //如下：topic 为 "TopicTest"，tag 为 "push"
                Message message = new Message("topic_family", "push", this.A().getBytes());
                SendResult result = producer.send(message,new MessageQueueSelector() {

                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();
                        return mqs.get(index);
                    }
                },1);
                System.out.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
            }
            stop.stop();
            System.out.println("----------------发送十条消息耗时：" + stop.getTotalTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

    private String A(){
        String str = "";
        str = str+ (char)(Math.random()*26+'A');
        return str;
    }
}

