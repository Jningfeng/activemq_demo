package com.jnf.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;


public class JmsProduce_AsyncSend {

    public static final String ACTIVEMQ_URL="tcp://192.168.204.178:61616";
   // public static final String ACTIVEMQ_URL="tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME="queue02";
    public static void main(String[] args) throws JMSException {

        //1.创建连接工厂 按照给定的URL地址 采用默认用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        activeMQConnectionFactory.setUseAsyncSend(true);
        //2.通过连接工厂 获得连接Connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建会话Session
        //两个参数  1.事务 2.签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地(是队列还是主题)
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消费生产者
       // MessageProducer producer = session.createProducer(queue);
        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer)session.createProducer(queue);
        //非持久
      //  producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //持久
      //  producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //6.通过使用producer生成3条消息发送到MQ队列里
        TextMessage  message = null ;
        for (int i = 1 ; i <= 9 ; i++){

            //7.创建消息
           message = session.createTextMessage("Msg---" + i);//理解为一个字符串
            message.setJMSMessageID(UUID.randomUUID().toString()+"-----orderJnf");
            String jmsMessageID = message.getJMSMessageID();
            activeMQMessageProducer.send(message, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(jmsMessageID+"has been ok send");
                }

                @Override
                public void onException(JMSException exception) {
                    System.out.println(jmsMessageID+"file to send to mq");
                }
            });
            //8.通过producer发送给MQ
            //producer.send(textMessage);
        }
        //9.关闭资源
        activeMQMessageProducer.close();
       // producer.close();
        session.close();
        connection.close();

        System.out.println("消息发送到MQ完成");

    }
}
