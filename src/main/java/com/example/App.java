package com.example;

public class App {

    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static void main(String[] args) {
        System.out.println("App chal raha hai");
        System.out.println("2 + 3 = " + add(2, 3));
        System.out.println("5 - 2 = " + subtract(5, 2));
    }
}
