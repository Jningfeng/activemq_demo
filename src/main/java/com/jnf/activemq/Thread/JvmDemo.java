package com.jnf.activemq.Thread;

class MyNumber{
   volatile   int  number = 10 ;

    public  void  To(){
        this.number = 100 ;
    }
}

public class JvmDemo {
    public static void main(String[] args) {
               MyNumber myNumber = new MyNumber();
               new Thread(() -> {
                   System.out.println(Thread.currentThread().getName()+"*******come in");
                   try {
                       Thread.sleep(300);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   myNumber.To();   //将 10 修改成 100
                   System.out.println(Thread.currentThread().getName()+"\t update number , number value :"+myNumber.number);
               },"A").start();

         while (myNumber.number == 10){
                   //需要有一种通知机制告诉main线程 ，number 已经修改 成 100  ， 跳出while
         }
        System.out.println(Thread.currentThread().getName()+"\t mission is over");



       /* System.out.println(Runtime.getRuntime().availableProcessors());
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();

        System.out.println("-Xmx:MAX_MEMORY="+maxMemory+"(字节)、"+(maxMemory /(double)1024 / 1024)+"MB");
        System.out.println("-Xms:TOTAL_MEMORY="+totalMemory+"(字节)、"+(totalMemory /(double)1024 / 1024)+"MB");*/

    }
}
