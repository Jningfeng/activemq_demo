package com.jnf.activemq.Thread;

import java.util.concurrent.TimeUnit;
//死锁案例   检查及定位（jps  jstack+进程号）
class HoldLockThread implements Runnable{
    private String lockA;
    private String lockB;
    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }
    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t自己持有："+lockA+"尝试获得:"+lockB);
            try {TimeUnit.SECONDS.sleep(2);}catch (Exception e){e.printStackTrace();}
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t自己持有："+lockB+"尝试获得:"+lockA);
            }
        }
    }
}
public class DeadLockDemo {
    public static void main(String[] args) {
      String lockA = "lockA";
      String lockB = "lockB";
      new Thread(new HoldLockThread(lockA,lockB),"AAA").start();
      new Thread(new HoldLockThread(lockB,lockA),"BBB").start();
    }
}
