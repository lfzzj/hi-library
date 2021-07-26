package com.lf.hi.library;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.library.demo.thread$
 * @ClassName: AtomicDemo$
 * @Author: LF
 * @CreateDate: 2021/7/12$ 10:28$
 * @Description:
 */
class AtomicDemo {

    public static void main(String[] args) throws InterruptedException {

        //基本用法
        ReentrantLock lock = new ReentrantLock(false);
        try {
            lock.lock();
            //……
        } finally {
            lock.unlock();
        }
        /**
         * void lock()//获取不到会阻塞
         * boolean tryLock()//尝试获取锁，成功返回true
         * boolean  tryLock(3000,TimeUnit.MILLISECONDS)//在一定时间内去不断尝试获取锁
         * void lockInterruptibly()//可使用Thread.interrupt()打断阻塞状态，退出竞争，让给其他线程
         */


        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5,
                100,
                10,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<>());
        for (int i = 0; i < 100; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {

                }
            });
        }


        AtomicTask tast = new AtomicTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    tast.incrementAtomic();
                    tast.incrementVolatile();
                }
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("原子类结果：" + tast.atomicInteger.get());
        System.out.println("volatile结果：" + tast.volatileCount);

    }

    static class AtomicTask {
        AtomicInteger atomicInteger = new AtomicInteger();
        volatile int volatileCount = 0;

        void incrementAtomic() {
            atomicInteger.getAndIncrement();
        }

        void incrementVolatile() {
            volatileCount++;
        }
    }
}
