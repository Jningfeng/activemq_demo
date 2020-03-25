package com.jnf.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class JmsProduce {

    public static final String ACTIVEMQ_URL="failover:(tcp://192.168.204.178:61616,tcp://192.168.204.178:61617,tcp://192.168.204.178:61618)?randomize=false";
   // public static final String ACTIVEMQ_URL="tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME="queue-cluster";
    public static void main(String[] args) throws JMSException {

        //1.创建连接工厂 按照给定的URL地址 采用默认用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂 获得连接Connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建会话Session
        //两个参数  1.事务 2.签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地(是队列还是主题)
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消费生产者
        MessageProducer producer = session.createProducer(queue);

        //非持久
      //  producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //持久
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //6.通过使用producer生成3条消息发送到MQ队列里
        for (int i = 1 ; i <= 3 ; i++){

            //7.创建消息
            TextMessage textMessage = session.createTextMessage("Msg---" + i);//理解为一个字符串
            //8.通过producer发送给MQ
            producer.send(textMessage);
        }
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();

        System.out.println("消息发送到MQ完成");

    }
}
