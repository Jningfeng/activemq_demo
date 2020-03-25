package com.jnf.activemq.Thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//读写锁
class Mycache{
    private volatile Map<String,Object> map = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key ,Object value) throws InterruptedException {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t----开始写入"+key);
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t-----写入完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
    public void get(String key) throws InterruptedException {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t读取数据");
            TimeUnit.MILLISECONDS.sleep(300);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t读取完成"+o);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
     Mycache mycache = new Mycache();

     for (int i = 1 ; i<=5 ; i++){
         final int tempInt = i;
         new Thread(() -> {
             try {
                 mycache.put(tempInt+"",tempInt+"");
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         },String.valueOf(i)).start();
     }

        for (int i = 1 ; i<=5 ; i++){
            final int tempInt = i;
            new Thread(() -> {
                try {
                    mycache.get(tempInt+"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }
}
