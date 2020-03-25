package com.jnf.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Topic {
    public static final String ACTIVEMQ_URL="tcp://192.168.204.177:61616";
    public static final String TOPIC_NAME="topic-Jnf";
    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("2");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        //监听(异步非阻塞)
        MessageConsumer consumer = session.createConsumer(topic);

     consumer.setMessageListener(( message) -> {
         if (null != message && message instanceof TextMessage){
             TextMessage textMessage = (TextMessage)  message;
             try {
                 System.out.println("消费者接收到消息："+textMessage.getText());
             } catch (JMSException e) {
                 e.printStackTrace();
             }
         }
    });

        //控制台灯一直亮
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
