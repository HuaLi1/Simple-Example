package com.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2、公平锁实现
 * 对于公平锁的实现，就要结合着我们的可重入性质了。公平锁的含义我们上面已经说了，就是谁等的时间最长，谁就先获取锁。
 * 首先new一个ReentrantLock的时候参数为true，表明实现公平锁机制。在这里我们多定义几个线程ABCDE，
 * 然后再test方法中循环执行了两次加锁和解锁的过程。
 * 3、非公平锁实现
 * 非公平锁那就随机的获取，谁运气好，cpu时间片轮到哪个线程，哪个线程就能获取锁，和上面公平锁的区别很简单，
 * 就在于先new一个ReentrantLock的时候参数为false，当然我们也可以不写，默认就是false。
 */
public class ReentrantLockTest2 {
    //参数为true，表明实现公平锁机制;若为false或不写，则为非公平锁实现
    private static final Lock lock = new ReentrantLock(true);
    public static void main(String[] args){
        new Thread(ReentrantLockTest2::test,"线程A").start();
        new Thread(ReentrantLockTest2::test,"线程B").start();
        new Thread(ReentrantLockTest2::test,"线程C").start();
        new Thread(ReentrantLockTest2::test,"线程D").start();
        new Thread(ReentrantLockTest2::test,"线程E").start();
        new Thread(ReentrantLockTest2::test,"线程F").start();
    }
    private static void test(){
        for (int i=0;i<2;i++){
            try{
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"获取了锁");
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                //System.out.println(Thread.currentThread().getName()+"释放了锁");
                lock.unlock();
            }
        }


    }
}
