package com.thread;

//模拟龟兔赛跑
public class ThreadRace implements Runnable{

    //胜利者
    private static String winner;

    @Override
    public void run() {
        //设置赛道200米
        for (int i=0;i<=200;i++){
            //魔力兔子偷懒,没20米偷懒
            if (Thread.currentThread().getName().equals("兔子") && i%20==0){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //判断是否比赛结束
            Boolean isOver = gameOver(i);
            if (isOver){
                break;
            }
            System.out.println(Thread.currentThread().getName()+"-->跑了"+i+"米");
        }

    }

    public static void main(String args[]){
        ThreadRace race = new ThreadRace();
        //给线程命名并启动
        new Thread(race,"兔子").start();
        new Thread(race,"乌龟").start();
    }



    private boolean gameOver(int length){
        //判断是否已经有胜利者
        if (winner != null){
            return true;
        }else if (length>=200){
            winner = Thread.currentThread().getName();
            System.out.println("胜利者是："+winner);
            return true;
        }
        return false;
    }

}
