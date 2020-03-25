package com.jnf.activemq.Thread;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object object1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        PhantomReference<Object> phantomReference = new PhantomReference<Object>(object1,referenceQueue);
        System.out.println(object1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
        System.out.println("===========");
        object1 = null ;
        System.gc();
        Thread.sleep(500);
        System.out.println(object1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }
}
