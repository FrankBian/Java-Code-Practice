package com.gansuer.practice.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Frank on 6/3/16.
 */
public class FunctionReference {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 98);

        //print all member
        eval(list, n -> true);

        //print even member

        eval(list, n -> n % 2 == 0);

        //print n >5
        eval(list, n -> n > 5);

    }

    public static void eval(List<Integer> list, Predicate<Integer> predicate) {
        list.forEach(item -> {
            if (predicate.test(item)) {
                System.out.print(item + " ");
            }
        });

        System.out.println();
    }
}
