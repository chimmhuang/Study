package com.chimm;

/**
 * @author chimmhuang
 * @date 2019/1/15 0015 10:07
 * @description
 */
public class Main {

    public static void main(String[] args) {
        int a = 56;
        int b = 42;
        System.out.println(a+"和"+b+"的最大公约数是："+gcd(a,b));
    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        int r = a % b;
        return gcd(b, r);
    }
}
