package com.jnf.activemq.Thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class  Tar{
    private int number = 1 ;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try {
            //判断
            while (number != 1){
                condition1.await();
            }
            //干活
            for (int i = 1 ; i<=5 ; i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //通知
            number = 2 ;
            condition2.signal();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void print10(){
        lock.lock();
        try {
            //判断
            while (number != 2){
                condition1.await();
            }
            //干活
            for (int i = 1 ; i<=10 ; i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //通知
            number = 3 ;
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void print15(){
        lock.lock();
        try {
            //判断
            while (number != 3){
                condition1.await();
            }
            //干活
            for (int i = 1 ; i<=15 ; i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //通知
            number = 1 ;
            condition1.signal();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}

public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {
       Tar tar = new Tar() ;
        new Thread(() -> {for (int i = 1 ; i<=10 ; i++){ tar.print5() ; }},"A").start();
        new Thread(() -> {for (int i = 1 ; i<=10 ; i++){ tar.print10(); }},"B").start();
        new Thread(() -> {for (int i = 1 ; i<=10 ; i++){ tar.print15(); }},"C").start();
    }
}
