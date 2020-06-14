package com.thread;

import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

    public static void main(String[] args){
        DemoLock lock = new DemoLock();
        new Thread(lock).start();
        new Thread(lock).start();
        new Thread(lock).start();
    }
}

class DemoLock implements Runnable{
    int tickeeNum=10;

    //定义lock锁
    private final ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        while (true){
            try{
                lock.lock();
                if (tickeeNum>0){
                    try {
                        Thread.sleep(1000);
                        System.out.println("tickeeNum:"+tickeeNum--);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    break;
                }
            }finally {
                //解锁
                lock.unlock();
            }

        }
    }
}
