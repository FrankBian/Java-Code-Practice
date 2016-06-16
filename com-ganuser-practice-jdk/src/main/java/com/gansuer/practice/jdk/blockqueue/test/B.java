package com.gansuer.practice.jdk.blockqueue.test;

/**
 * Created by Frank on 5/25/16.
 */
public class B extends A {

    public String helloB(String name) {
        return sayHello(name);
    }

    public static void main(String[] args){
        B b = new B();
        String res =b.sayHello("B");
        System.out.println(res);
        System.out.println(b.helloB("b~A"));
    }
}
