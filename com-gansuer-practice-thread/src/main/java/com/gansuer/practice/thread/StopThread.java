package com.gansuer.practice.thread;

/**
 * Created by Frank on 5/31/2016.
 */
public class StopThread {

    static class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            for (int i= 0; i< 500000; i++){
                System.out.println("i=" + (i+1));
            }
        }
    }

    public static void main(String[] args){
        MyThread thread = new MyThread();
        thread.start();
        try {
            Thread.sleep(2000);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
