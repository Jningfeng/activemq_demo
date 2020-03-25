package com.jnf.activemq.Thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/*
* 第3种获得多线程的方式
*  get() 请放最后一行
* */

class MyThread implements java.util.concurrent.Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("******come in here");
        try {
            TimeUnit.SECONDS.sleep(4);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1024;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask futureTask = new FutureTask(new MyThread());

        new Thread(futureTask,"A").start();

        System.out.println(futureTask.get());

    }

}
