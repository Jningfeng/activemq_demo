package com.jnf.activemq.Thread;


import java.util.concurrent.*;

//第4种获得java多线程的方式，线程池
public class ThreadPoolDemo {

    public static void main(String[] args) {
        //电脑核数
       // System.out.println(Runtime.getRuntime().availableProcessors());
       // ExecutorService executorService = Executors.newFixedThreadPool(5);  //一池5个 固定线程池
       // ExecutorService executorService = Executors.newSingleThreadExecutor() ; //一池一个  单个线程池
       //   ExecutorService executorService = Executors.newCachedThreadPool() ;   // 一池N个   可变线程池

        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 1 ; i <= 8 ; i++){
             //   TimeUnit.SECONDS.sleep(3);
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
    }
}
