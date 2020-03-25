package com.jnf.activemq.Thread;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


/*
* 集合类是不安全的
*
* 1.故障现象
* java.util.ConcurrentModificationException
*
* 2.导致原因
* 并发争抢修改导致
*
* 3.解决方案d
*  3.1 : Vector<>();
*  3.2 : Collections.synchronizedList(new ArrayList<>());
*  3.3 : new CopyOnWriteArrayList();
* 4.优化建议 （同样的错误不出现第二次）
* */

public class ListSetMap {

    public static void main(String[] args) {

    }
    public static void listNotsafe(){
        /* List<String> list = Arrays.asList("a,b,c");
       list.forEach(System.out::println);*/
        List<String> list = new CopyOnWriteArrayList();
        for (int i = 1 ; i <= 30 ; i++){
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }

    }
    public static void setNotsafe() {
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 1 ; i <= 30 ; i++){
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }

    }
    public static void hashNotsafe(){
        Map<String,String> map = new ConcurrentHashMap<>();
        for (int i = 1 ; i <= 30 ; i++){
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
