package com.jnf.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;


@Service
public class SpringMQ_Produce {
        @Autowired
         private JmsTemplate jmsTemplate ;
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Produce springMQ_produce =(SpringMQ_Produce) applicationContext.getBean("springMQ_Produce");
        springMQ_produce.jmsTemplate.send((Session session) -> {
            TextMessage textMessage = session.createTextMessage("Spring和ActiveMQ的整合case");
            return  textMessage ;
        });
        System.out.println("成功发布");

    }
}
