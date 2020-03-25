package com.jnf.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;

public class JmsConsumer {
    public static final String ACTIVEMQ_URL="failover:(tcp://192.168.204.178:61616,tcp://192.168.204.178:61617,tcp://192.168.204.178:61618)?randomize=false";
  //  public static final String ACTIVEMQ_URL="tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME="queue-cluster";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //设置重发策略 默认 6
        /*RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);*/

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
        //session.commit();
        consumer.close();
        session.close();
        connection.close();
    }
}
