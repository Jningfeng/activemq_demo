package com.jnf.activemq.Thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
// ABA问题的产生和解决
public class ABADemo {
      static AtomicReference atomicReference = new AtomicReference(100);
      static AtomicStampedReference atomicStampedReference = new AtomicStampedReference(100,1);

    public static void main(String[] args) {
          //###################################
        System.out.println();
        System.out.println("===========ABA问题产生");
        new Thread(() -> {
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);},"t1").start();

        new Thread(() -> {
            try {TimeUnit.SECONDS.sleep(1); }catch (Exception e){ e.printStackTrace();}
            System.out.println(atomicReference.compareAndSet(100, 2019)+"\t"+atomicReference.get());
            },"t2").start();
         try {TimeUnit.SECONDS.sleep(2); }catch (Exception e){ e.printStackTrace();}

            System.out.println("===========ABA问题解决");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第1次版本号："+stamp);
            try {TimeUnit.SECONDS.sleep(1); }catch (Exception e){ e.printStackTrace();}
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第2次版本号："+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第3次版本号："+atomicStampedReference.getStamp());
            },"t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第1次版本号："+stamp);
            try {TimeUnit.SECONDS.sleep(3); }catch (Exception e){ e.printStackTrace();}
            boolean b = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+"\t修改成功否："+b+"\t当前最新实际版本号："+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t当前实际最新值："+atomicStampedReference.getReference());
        },"t4").start();
    }
}
