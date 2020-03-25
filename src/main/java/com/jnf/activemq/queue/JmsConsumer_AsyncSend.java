package com.jnf.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsumer_AsyncSend {
    public static final String ACTIVEMQ_URL="tcp://192.168.204.178:61616";
    // public static final String ACTIVEMQ_URL="tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME="queue02";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        //同步阻塞
        while (true){
            //名字与生产者一样
            TextMessage textMessage = (TextMessage) consumer.receive(3000L);
            if (null != textMessage){
                System.out.println("消费者接收到消息："+textMessage.getText());
            }else {
                break;
            }
        }
        consumer.close();
        session.close();
        connection.close();
    }
}
