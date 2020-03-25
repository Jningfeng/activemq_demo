package com.jnf.activemq.Thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *  线程 操作 资源类
 *  判断  干活  通知
 *  多线程交互中 必须要防止多线程的虚假唤醒 （判读只用while , 不用 if）
 *  标志位
 */
class Ticket {
    private int number = 30;
    private Lock lock = new ReentrantLock();

    public void saleTicket() {

        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t卖出第：" + (number--) + "\t还剩第：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class Test {

    public static void main(String[] args)throws Exception {
        Ticket ticket = new Ticket();

         new Thread(() -> {for ( int i= 1; i<=30  ;i++) ticket.saleTicket(); },"A").start();
         new Thread(() -> {for ( int i= 1; i<=30  ;i++) ticket.saleTicket(); },"B").start();
         new Thread(() -> {for ( int i= 1; i<=30  ;i++) ticket.saleTicket(); },"C").start();



    /*    new Thread(new Runnable() {
        @Override
        public void run() {
            for ( int i= 1; i<=30 ;i++){
                ticket.saleTicket();
            }
        }
    },"A").start();

        new Thread(new Runnable() {
        @Override
        public void run() {
            for ( int i= 1; i<=30 ; i++){
                ticket.saleTicket();
            }
        }
    },"B").start();

        new Thread(new Runnable() {
        @Override
        public void run() {
            for ( int i= 1; i<=30 ;i++){
                ticket.saleTicket();
            }
        }
    },"C").start();*/

}
}
