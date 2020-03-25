package com.jnf.activemq.Thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyDate{
   volatile int number = 0 ;

    public void add(){
        this.number = 60 ;
    }

    public  void addPlus(){
        number++ ;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void Myatomic(){
        atomicInteger.getAndIncrement();
    }
}

public class VolatileDemo {

    public static void main(String[] args) {

         MyDate myDate = new MyDate();
         for (int i = 1 ; i <= 20 ;i++){
             new  Thread(() -> {
                 for (int j = 1 ; j<= 1000;j++){
                     myDate.addPlus();
                     myDate.Myatomic();
                 }
             },String.valueOf(i)).start();
         }

         while (Thread.activeCount() > 2){
             Thread.yield();
         }
        System.out.println(Thread.currentThread().getName()+"\t int type, finally number value:"+myDate.number);
        System.out.println(Thread.currentThread().getName()+"\t atomicInteger type, finally number value:"+myDate.atomicInteger);
    }

    public static void seeOkByVolatile(){
        MyDate myDate = new MyDate();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t come in");
            //暂停一会线程
            try { TimeUnit.SECONDS.sleep(3); }catch (Exception e){ e.printStackTrace(); }
            myDate.add();
            System.out.println(Thread.currentThread().getName()+"\t update number value:"+myDate.number);
        },"AAA").start();

        //第二个线程就是main线程
        while (myDate.number == 0){
            //main在这样一种循环等待  直到 number不等于0
        }
        System.out.println(Thread.currentThread().getName()+"\t mission is over");
    }
}


