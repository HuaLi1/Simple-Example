package com.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 的简单实用
 * java除了使用关键字synchronized外，还可以使用ReentrantLock实现独占锁的功能。
 * ReentrantLock 锁有好几种，除了常用的lock ，tryLock ，其中有个lockInterruptibly 。
 * 1）lock(), 拿不到lock就不罢休，不然线程就一直block。 比较无赖的做法。
 * 2）tryLock()，马上返回，拿到lock就返回true，不然返回false。 比较潇洒的做法。
 *      带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false。比较聪明的做法。
 * 锁的详细介绍：往下拉
 */
public class ReentrantLockTest {
    private static final Lock lock = new ReentrantLock();
    public static void main(String[] args){
        new Thread(ReentrantLockTest::test,"线程A").start();
        new Thread(ReentrantLockTest::test,"线程B").start();
        new Thread(ReentrantLockTest::test,"线程C").start();
    }
    private static void test(){
        try{
            //获取锁，拿不到lock就不罢休，不然线程就一直block。
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"获取了锁");
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            System.out.println(Thread.currentThread().getName()+"释放了锁");
            lock.unlock();
        }

    }
}
/**
 * lock
 * public void lock()
 * 获取锁。
 * 如果该锁没有被另一个线程保持，则获取该锁并立即返回，将锁的保持计数设置为 1。
 * 如果当前线程已经保持该锁，则将保持计数加 1，并且该方法立即返回。
 * 如果该锁被另一个线程保持，则出于线程调度的目的，禁用当前线程，并且在获得锁之前，该线程将一
 * 直处于休眠状态，此时锁保持计数被设置为 1。
 *
 *
 * lockInterruptibly
 * public void lockInterruptibly() throws InterruptedException
 * 1）如果当前线程未被中断，则获取锁。
 *
 * 2）如果该锁没有被另一个线程保持，则获取该锁并立即返回，将锁的保持计数设置为 1。
 *
 * 3）如果当前线程已经保持此锁，则将保持计数加 1，并且该方法立即返回。
 *
 * 4）如果锁被另一个线程保持，则出于线程调度目的，禁用当前线程，并且在发生以下两种情况之一以
 * 前，该线程将一直处于休眠状态：
 *      1）锁由当前线程获得；或者
 *
 *      2）其他某个线程中断当前线程。
 *
 * 5）如果当前线程获得该锁，则将锁保持计数设置为 1。
 *    如果当前线程：
 *        1）在进入此方法时已经设置了该线程的中断状态；或者
 *
 *        2）在等待获取锁的同时被中断。
 *
 *    则抛出 InterruptedException，并且清除当前线程的已中断状态。
 *
 *
 * 6）在此实现中，因为此方法是一个显式中断点，所以要优先考虑响应中断，而不是响应锁的普通获取或
 * 重入获取。
 *
 *
 * tryLock    public boolean tryLock()
 *
 * 仅在调用时锁未被另一个线程保持的情况下，才获取该锁。
 *
 * 1）如果该锁没有被另一个线程保持，并且立即返回 true 值，则将锁的保持计数设置为 1。
 * 即使已将此锁设置为使用公平排序策略，但是调用 tryLock() 仍将 立即获取锁（如果有可用的），
 * 而不管其他线程当前是否正在等待该锁。在某些情况下，此“闯入”行为可能很有用，即使它会打破公
 * 平性也如此。如果希望遵守此锁的公平设置，则使用 tryLock(0, TimeUnit.SECONDS)
 * ，它几乎是等效的（也检测中断）。
 *
 * 2）如果当前线程已经保持此锁，则将保持计数加 1，该方法将返回 true。
 *
 * 3）如果锁被另一个线程保持，则此方法将立即返回 false 值。
 *
 * 指定者：
 *    接口 Lock 中的  tryLock
 * 返回：
 *    如果锁是自由的并且被当前线程获取，或者当前线程已经保持该锁，则返回 true；否则返回
 * false
 */
