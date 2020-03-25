package com.jnf.activemq.Thread;

import java.lang.ref.WeakReference;
//弱引用
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object  object1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(object1);
        System.out.println(object1);
        System.out.println(weakReference.get());
        object1=null;
        System.gc();
        System.out.println(object1);
        System.out.println(weakReference.get());
    }
}
