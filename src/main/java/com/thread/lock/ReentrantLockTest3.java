package com.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 相应中断    对比ReentrantLockTest4
 * 响应中断就是一个线程获取不到锁，不会傻傻的一直等下去，ReentrantLock会给予一个中断回应。在这里我们举一个死锁的案例。
 * 在这里我们定义了两个锁lock1和lock2。然后使用两个线程thread和thread1构造死锁场景。正常情况下，这两个线程相互等待获取资源
 * 而处于死循环状态。但是我们此时thread中断，另外一个线程就可以获取资源，正常地执行了。
 */
public class ReentrantLockTest3 {
    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();
    public static void main(String[] args){
        Thread thread = new Thread(new ThreadDemo(lock1,lock2));
        Thread thread1 = new Thread(new ThreadDemo(lock2,lock1));
        thread.start();
        thread1.start();
        thread.interrupt();//是第一个线程中断
    }
    static class ThreadDemo implements Runnable{
        Lock firstLock;
        Lock secondLock;
        public ThreadDemo(Lock firstLock,Lock secondLock){
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }
        @Override
        public void run() {
            try {
                firstLock.lockInterruptibly();
                TimeUnit.MILLISECONDS.sleep(50);
                secondLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName()+"获取到了资源，正常结束");
            }
        }
    }
}
