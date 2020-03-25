package com.jnf.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_Topic_Persist {
public static final String ACTIVEMQ_URL="tcp://192.168.204.177:61616";

public static final String TOPIC_NAME="topic-Jnf";
    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
        for (int i = 1 ; i<=3 ; i++){
            TextMessage textMessage = session.createTextMessage("topic---" + i);
            producer.send(textMessage);
        }
        producer.close();
        session.close();
        connection.close();

        System.out.println("带持久化的topic消息发布到MQ完成");
    }
}
