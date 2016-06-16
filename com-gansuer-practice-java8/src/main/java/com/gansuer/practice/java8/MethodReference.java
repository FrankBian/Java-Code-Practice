package com.gansuer.practice.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 6/3/16.
 */
public class MethodReference {

    public static void main(String[] args){
        // method reference

        List names = new ArrayList<>();
        names.add("bian");
        names.add("boo");
        names.add("frank");

        names.forEach(name-> System.out.println("method reference : " + name));
    }
}
