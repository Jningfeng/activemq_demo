package com.jnf.activemq.Thread;


import java.lang.ref.SoftReference;

public class SoftReferenceDemo {
//内存够用保留，不够用就回收
    public static void softRef_Memory_Enough(){
          Object  object1 = new Object();
          SoftReference<Object> softReference = new SoftReference<>(object1);
          System.out.println(object1);
          System.out.println(softReference.get());
          object1=null ;
          System.gc();
          System.out.println(object1);
          System.out.println(softReference.get());
    }
    /**
     * JVM配置 故意产生大对象并配置小内存，让他内存不够用导致OOM，看软引用的回收情况
     * -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    public static void  softRef_Memory_NotEnough(){
        Object  object1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(object1);
        System.out.println(object1);
        System.out.println(softReference.get());
        object1=null ;
        System.gc();
        try {
            byte[] buteArray = new byte[30 * 1024 * 1024];
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(object1);
            System.out.println(softReference.get());
        }
    }

    public static void main(String[] args) {

     //   softRef_Memory_Enough();
          softRef_Memory_NotEnough();
    }
}
