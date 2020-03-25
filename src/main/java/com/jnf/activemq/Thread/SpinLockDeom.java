package com.jnf.activemq.Thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDeom {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in");
        while (! atomicReference.compareAndSet(null,thread)){
        }
    }
    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnlock");
    }
    public static void main(String[] args) {
        SpinLockDeom spinLockDeom = new SpinLockDeom();
        new Thread(()->{
            spinLockDeom.myLock();
            try {TimeUnit.SECONDS.sleep(5);}catch (Exception e){ e.printStackTrace();}
            spinLockDeom.myUnlock();
        },"AA").start();
        try {TimeUnit.SECONDS.sleep(1);}catch (Exception e){ e.printStackTrace();}
        new Thread(()->{spinLockDeom.myLock();spinLockDeom.myUnlock();},"BB").start();
    }
}
