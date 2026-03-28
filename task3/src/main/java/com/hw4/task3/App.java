package com.hw4.task3;

public class App {
    public static void main(String[] args) {
        DemoService svc = new DemoService();
        int a = args.length > 0 ? Integer.parseInt(args[0]) : 3;
        int b = args.length > 1 ? Integer.parseInt(args[1]) : 4;
        System.out.println("max(" + a + "," + b + ")=" + svc.max(a, b));
        System.out.println("sign(" + a + ")=" + svc.sign(a));
    }
}
