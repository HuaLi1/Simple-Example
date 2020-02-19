package com.rocketmq.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;

import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Service
public class Consumer {
    /**
     * 消费者的组名
     */
    @Value("${rocketMq.consumerGroup}")
    private String consumerGroup;

    /**
     * NameServer 地址
     */
    @Value("${rocketMq.NAME_SERVER}")
    private String namesrvAddr;

    private DefaultMQPushConsumer consumer;
    //初始化消费者
    @PostConstruct
    private void initConsumer(){

        consumer = new DefaultMQPushConsumer(consumerGroup);
        //指定NameServer地址，多个地址以 ; 隔开
        consumer.setNamesrvAddr(namesrvAddr);

    }

    public void consumerMsg() {
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("topic_family", "push");

            //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
            //如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {//->为Java8的lambda表达式,就是匿名函数,具体可以参考该文章https://segmentfault.com/q/1010000007518474。
                try {
                    for (MessageExt messageExt : list) {

                        System.out.println("messageExt: " + messageExt);//输出消息内容

                        String messageBody = new String(messageExt.getBody());

                        System.out.println("消费响应：msgId : " + messageExt.getMsgId() + ",  msgBody : " + messageBody);//输出消息内容
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER; //稍后再试
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
