package com.jnf.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.sql.SQLOutput;

public class JmsConsumer_Topic_Persist {
    public static final String ACTIVEMQ_URL="tcp://192.168.204.177:61616";

    public static final String TOPIC_NAME="topic-Jnf";
    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("z4");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z4");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "Abc");
        connection.start();

        Message receive = topicSubscriber.receive();

       while (null != receive){
           TextMessage textMessage = (TextMessage)receive;
           System.out.println("接收到topic持久化消息"+textMessage.getText());
           receive =topicSubscriber.receive(3000L);
       }


        session.close();
        connection.close();
    }
}
