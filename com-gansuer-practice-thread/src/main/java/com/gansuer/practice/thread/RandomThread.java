package com.gansuer.practice.thread;

/**
 * Created by Frank on 5/31/2016.
 */
public class RandomThread {
    static class MyThread extends Thread{

        public MyThread() {
            System.out.println("thread constructor begin -----");
            System.out.println("thread name : " + Thread.currentThread().getName());
            System.out.println("thread constructor end -----");
        }

        @Override
        public void run() {
            try{
                for (int i = 0; i< 10 ; i++){
                    int time = (int)(Math.random()*1000);
                    Thread.sleep(time);
                    System.out.println("run = " + Thread.currentThread().getName());
                }
                System.out.println("Thread isAlive() in run : " + this.isAlive());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        try{
            MyThread thread = new MyThread();
            thread.setName("myThread");
            System.out.println("Thread isAlive() : " + thread.isAlive());
            thread.start();
            for (int i = 0; i< 10 ; i++){
                int time = (int)(Math.random()*1000);
                Thread.sleep(time);
                System.out.println("run = " + Thread.currentThread().getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
