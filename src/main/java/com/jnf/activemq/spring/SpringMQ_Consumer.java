package com.jnf.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@Service
public class SpringMQ_Consumer {
    @Autowired
    private JmsTemplate jmsTemplate ;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Consumer springMQ_consumer = (SpringMQ_Consumer)applicationContext.getBean("springMQ_Consumer");
        String receiveAndConvert =(String) springMQ_consumer.jmsTemplate.receiveAndConvert();
        System.out.println("消费者收到消息："+receiveAndConvert);
    }
 }
