package com.jnf.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;


public class JmsProduce_DelayAndSchedule {

    public static final String ACTIVEMQ_URL="tcp://192.168.204.178:61616";
    public static final String QUEUE_NAME="queue-delay";
    public static void main(String[] args) throws JMSException {


        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageProducer producer = session.createProducer(queue);

        long delay = 3*1000 ;  //延迟投递的时间
        long period = 4*1000 ;   //重复投递的时间间隔
        int  repeat = 4 ;   //重复投递次数


        for (int i = 1 ; i <= 3 ; i++){

            TextMessage textMessage = session.createTextMessage(" delay Message---" + i);//理解为一个字符串

            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,delay);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD,period);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT,repeat);

            producer.send(textMessage);
        }
        producer.close();
        session.close();
        connection.close();

        System.out.println("消息发送到MQ完成");

    }
}
