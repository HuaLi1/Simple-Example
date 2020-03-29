package com.thread;

//创建线程：继承Thread
public class ThreadDemo2 extends Thread{
    @Override
    public void run() {
        for (int i=0;i<200;i++){
            System.out.println("-------"+i+"--------");
        }
    }

    public static void main(String args[]){
        ThreadDemo2 threadDemo2 = new ThreadDemo2();
        //启动线程
        threadDemo2.start();
        for (int i=0;i<500;i++){
            System.out.println("*******"+i+"********");
        }

    }
}
