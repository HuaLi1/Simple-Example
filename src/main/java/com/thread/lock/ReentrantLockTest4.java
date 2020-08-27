package com.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 5、限时等待   对比ReentrantLockTest3
 * 这个是什么意思呢？也就是通过我们的tryLock方法来实现，可以选择传入时间参数，表示等待指定的时间，无参则表示立即返回锁申请的结果：
 * true表示获取锁成功，false表示获取锁失败。我们可以将这种方法用来解决死锁问题。
 *
 * 首先还是测试代码，不过在这里我们不需要再去中断其中的线程了，我们直接看线程类是如何实现的。
 * 在这个案例中，一个线程获取lock1时候第一次失败，那就等10毫秒之后第二次获取，就这样一直不停的调试，一直等到获取到相应的资源为止。
 *
 * 当然，我们可以设置tryLock的超时等待时间tryLock(long timeout,TimeUnit unit)，也就是说一个线程在指定的时间内没有获取锁，
 * 那就会返回false，就可以再去做其他事了。
 */
public class ReentrantLockTest4 {
    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();
    public static void main(String[] args){
        Thread thread = new Thread(new ReentrantLockTest4.ThreadDemo(lock1,lock2));
        Thread thread1 = new Thread(new ReentrantLockTest4.ThreadDemo(lock2,lock1));
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
                if (!lock1.tryLock()){
                    TimeUnit.MILLISECONDS.sleep(10);
                }
                if (!lock2.tryLock()){
                    TimeUnit.MILLISECONDS.sleep(10);
                }
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
