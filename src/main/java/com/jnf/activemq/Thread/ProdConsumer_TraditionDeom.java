package com.jnf.activemq.Thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//线程通信之生产消费者传统版
//判断为while不用if
class Air{
       private  int number = 0 ;
       private Lock lock = new ReentrantLock() ;
       private Condition condition = lock.newCondition();

    public  void  jia () throws InterruptedException {
        lock.lock();
        try {
            while (number != 0){
                condition.await();
            }
            number++ ;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public  void  jian () throws InterruptedException {
        lock.lock();
        try {
            while (number == 0){
                condition.await();
            }
            number-- ;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

/*
       public synchronized void  jia () throws InterruptedException {
           //判断
           while (number != 0 ){
               this.wait();
           }
           //干活
           number++ ;
           System.out.println(Thread.currentThread().getName()+"\t"+number);
           //通知
           this.notifyAll();
       }
    public  synchronized void  jian () throws InterruptedException {
        //判断
        while (number == 0 ){
            this.wait();
        }
        //干活
        number-- ;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        //通知
        this.notifyAll();
    }*/

}
public class ProdConsumer_TraditionDeom {
    public static void main(String[] args) {

           Air air = new Air();

           new Thread(() -> {

               for ( int i =1 ; i<=10 ; i++){
                   try {
                       Thread.sleep(200);
                       air.jia();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           },"A").start();

        new Thread(() -> {
            for ( int i =1 ; i<=10 ; i++){
                try {
                    Thread.sleep(300);
                    air.jian();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
        new Thread(() -> {
            for ( int i =1 ; i<=10 ; i++){
                try {
                    Thread.sleep(400);
                    air.jia();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
        new Thread(() -> {
            for ( int i =1 ; i<=10 ; i++){
                try {
                    Thread.sleep(500);
                    air.jian();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}
