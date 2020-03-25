package com.jnf.activemq.Thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
//线程通信之生产消费者阻塞队列版
class MyResource{
    private volatile boolean FLAG = true ; //默认开启进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null ;
    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
    public void myProd() throws InterruptedException {
        String  data = null ;
        boolean retValue ;
        while (FLAG){
            data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2, TimeUnit.SECONDS);
            if (retValue){
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"成功");
            }else {
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t 停!!!,表示FLAG=false,生产动作结束");
    }
    public void myConsumer() throws InterruptedException {
        String result = null ;
        while (FLAG){
            result = blockingQueue.poll(2,TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")){
                FLAG = false ;
                System.out.println(Thread.currentThread().getName()+"\t超过两秒钟没有取到蛋糕，消费退出");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t消费队列蛋糕"+result+"成功");
        }
    }
    public void stop(){
           this.FLAG=false;
    }
}
public class ProdConsumer_BlockingQueueDemo {
    public static void main(String[] args) {
       MyResource  myResource = new MyResource(new ArrayBlockingQueue<String>(10)) ;
       new Thread(() -> {
           System.out.println(Thread.currentThread().getName()+"\t生产线程启动");
           try {
               myResource.myProd();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       },"Prod").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t消费线程启动");
            try {
                myResource.myConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consumer").start();
        try {TimeUnit.SECONDS.sleep(5);}catch (Exception e){ e.printStackTrace();}
        System.out.println("5秒时间到，停，活动结束");
        myResource.stop();
    }
}
