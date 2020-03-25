package com.jnf.activemq.Thread;

/**
 * 强引用
 */
public class ReferenceDemo {
    public static void main(String[] args) {
        Object object1 = new Object();  //这样定义的就是强引用
        Object object2 = object1 ;   //obj2引用赋值
        object1 = null ; //置空
        System.gc();
        System.out.println(object2);

    }
}
