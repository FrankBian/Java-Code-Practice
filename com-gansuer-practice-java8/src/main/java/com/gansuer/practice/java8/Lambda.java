package com.gansuer.practice.java8;

/**
 * Created by Frank on 6/3/16.
 */
public class Lambda {

    static final String str = "Hello";

    public static void main(String[] args) {
        Lambda lambda = new Lambda();

        MathOperation addition = (int a, int b) -> a + b;

        MathOperation subtraction = (a, b) -> a - b;

        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 =" + lambda.operate(10, 5, addition));
        System.out.println("10 - 5 =" + lambda.operate(10, 5, subtraction));
        System.out.println("10 * 5 =" + lambda.operate(10, 5, multiplication));
        System.out.println("10 / 5 =" + lambda.operate(10, 5, division));

        GreetingService service1 = message -> System.out.println("Hello " + message);
        GreetingService service2 = (message) -> System.out.println("Hello " + message);

        service1.sayMessage("bob");
        service2.sayMessage("frank");

        GreetingService service3 = message -> System.out.println(str + " " + message);

        service3.sayMessage("bian");


    }

    interface MathOperation {
        int operate(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operate(a, b);
    }
}
