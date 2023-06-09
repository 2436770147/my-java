package com.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

//阻塞队列
public class BlockingQueueTest {

    //和ArrayList不同，ArrayBlockingQueue容量是有界的，需要给定容量大小
    static ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(3);

    //linkedBlockingQueue理论有界，默认Integer类的最大值,也可以指定大小
    static LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();

    //同步队列，生产者消费者模式
    static SynchronousQueue synchronousQueue=new SynchronousQueue();

    //阻塞带异常
    static void hasException(){
        //添加队列
        System.out.println(arrayBlockingQueue.add("a"));
        System.out.println(arrayBlockingQueue.add("b"));
        System.out.println(arrayBlockingQueue.add("c"));
        //插入时队列满则报异常
//        System.out.println(arrayBlockingQueue.add("d"));

        //删除队列，先进先出
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());
        //获取时队列为空则报异常
        System.out.println(arrayBlockingQueue.remove());
    }

    //阻塞带返回值、带超时
    static void hasResponseAndTimeout(){
        //和ArrayList不同，ArrayBlockingQueue容量是有界的，需要给定容量大小
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(3);

        //添加队列
        try {
            System.out.println(arrayBlockingQueue.offer("a",3000,TimeUnit.MILLISECONDS));
            System.out.println(arrayBlockingQueue.offer("b",3000,TimeUnit.MILLISECONDS));
            System.out.println(arrayBlockingQueue.offer("c",3000,TimeUnit.MILLISECONDS));
            System.out.println(arrayBlockingQueue.size());
            //插入时队列满，超时3秒后返回false
            System.out.println(arrayBlockingQueue.offer("d",3000,TimeUnit.MILLISECONDS));

            //删除队列，先进先出
            System.out.println(arrayBlockingQueue.poll(3000,TimeUnit.MILLISECONDS));
            System.out.println(arrayBlockingQueue.poll(3000,TimeUnit.MILLISECONDS));
            System.out.println(arrayBlockingQueue.poll(3000,TimeUnit.MILLISECONDS));
            //获取时队列为空超时3秒后返回null
            System.out.println(arrayBlockingQueue.poll(3000,TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //阻塞强行等待
    static void hasWaiting(){
        //和ArrayList不同，ArrayBlockingQueue容量是有界的，需要给定容量大小
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(3);

        try {
            //添加队列
            arrayBlockingQueue.put("a");
            arrayBlockingQueue.put("b");
            arrayBlockingQueue.put("c");
            System.out.println("************");
            //插入时队列满则强行等待
//            arrayBlockingQueue.put("d");

            //删除队列，先进先出
            arrayBlockingQueue.take();
            arrayBlockingQueue.take();
            arrayBlockingQueue.take();
            //获取时队列为空则强行等待
            arrayBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //生产者消费者模式
    static void ProducerAndConsumer(){
        new Thread(()->{
            try {
                //生产一个
                synchronousQueue.put("a");
                System.out.println(Thread.currentThread().getName()+" invoked put(a)");

                //生产一个
                synchronousQueue.put("b");
                System.out.println(Thread.currentThread().getName()+" invoked put(b)");

                //生产一个
                synchronousQueue.put("c");
                System.out.println(Thread.currentThread().getName()+" invoked put(c)");

                //此时没有消费需求，被阻塞
                synchronousQueue.put("d");
                System.out.println(Thread.currentThread().getName()+" invoked put(d)");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-A").start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
                //消费一个
                synchronousQueue.take();
                System.out.println(Thread.currentThread().getName()+"\t"+"a");

                Thread.sleep(3000);
                //消费一个
                synchronousQueue.take();
                System.out.println(Thread.currentThread().getName()+"\t"+"b");

                Thread.sleep(3000);
                //消费一个
                synchronousQueue.take();
                System.out.println(Thread.currentThread().getName()+"\t"+"c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-B").start();
    }

    public static void main(String[] args) {
        //阻塞带异常
//        hasException();

        //阻塞带返回值、带超时
//        hasResponseAndTimeout();

        //阻塞强行等待
//        hasWaiting();

        //生产者消费者模式
        ProducerAndConsumer();
    }
}
