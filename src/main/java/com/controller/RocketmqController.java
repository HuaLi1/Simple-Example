package com.controller;

import com.rocketmq.consumer.Consumer;
import com.rocketmq.producer.Producer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RocketmqController {

    private static final Log LOG = LogFactory.getLog(RocketmqController.class);

    @Autowired
    private Producer producer;

    @Autowired
    private Consumer consumer;
    private List<String> mesList;

 

    @RequestMapping("/testMQ")
    public String testMq() {
        producer.sendMsg();
        consumer.consumerMsg();
        return "success";
    }
}
