package com.jnf.activemq.Thread;

public class SingletonDemo {
    private static volatile SingletonDemo instance = null ;
    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+"\t 我是构造方法SingletonDemo()");
    }
    //DCL (Doouble check lock 双端检索机制)
    public static  SingletonDemo getInstance(){
        if (instance ==null){

            synchronized (SingletonDemo.class){
                if (instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance ;
    }
    public static void main(String[] args) {
        //单线程
       /* System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());*/
        for (int i =1 ; i<=10;i++){
            new Thread(() -> {
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
