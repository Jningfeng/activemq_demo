package com.jnf.activemq.Thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable{
    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS");
        sendEmail();
    }
    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"\t #####invoked sendEmail");
    }
    //############################
    Lock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }
    public void get(){
        lock.lock();
        try {System.out.println(Thread.currentThread().getName()+"\t invoked get()");set(); }finally {lock.unlock(); }
    }
    public void set(){
        lock.lock();
        try {System.out.println(Thread.currentThread().getName()+"\t #####invoked set()"); }finally { lock.unlock(); }
    }
}
public class ReentrantLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> { phone.sendSMS(); },"t1").start();
        new Thread(() -> {phone.sendSMS(); },"t2").start();
        try {TimeUnit.SECONDS.sleep(1);}catch (Exception e){ e.printStackTrace(); }
        Thread t3 = new Thread(phone,"t3");
        Thread t4 = new Thread(phone,"t4");
        t3.start();
        t4.start();

    }
}
