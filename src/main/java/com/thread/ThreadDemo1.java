package com.thread;

//创建线程：实现Runnable接口
public class ThreadDemo1 implements Runnable{
    @Override
    public void run() {
        for (int i=0;i<200;i++){
            System.out.println("-------"+i+"--------");
        }
    }

    public static void main(String args[]){
        ThreadDemo1 threadDemo1 = new ThreadDemo1();

        //创建线程对象，代理
//        Thread thread = new Thread(threadDemo1);
//        thread.start();
        //启动线程
        new Thread(threadDemo1).start();
        for (int i=0;i<500;i++){
            System.out.println("*******"+i+"********");
        }

    }
}
