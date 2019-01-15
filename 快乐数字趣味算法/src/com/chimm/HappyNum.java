package com.chimm;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chimmhuang
 * @date 2019/1/15 0015 17:04
 * @description 快乐数字趣味算法
 *
 * 判断一个数是不是快乐数字，将该数字拆分，然后将拆分后的数字平方，相加，递归运算，最后结果为1，并且和唯一，则是快乐数字，如：19就是，11不是
 * 1*1 + 9*9 = 82
 * 8*8 + 2*2 = 68
 * 6*6 + 8*8 = 100
 * 1*1 + 0*0 + 0*0 = 1
 * ---------------------------
 * 1*1 + 1*1 = 2
 * 2*2 = 4
 * 4*4 = 16
 * 1*1 + 6*6 = 37
 * 3*3 + 7*7 = 58
 * 5*5 + 8*8 = 89
 * 8*8 + 9*9 = 145
 * 1*1 + 4*4 + 5*5 = 42
 * 4*4 + 2*2 = 20
 * 2*2 + 0*0 = 4
 *
 */
public class HappyNum {

    /**
     * 判断一个数字是否为快乐数字
     * @param number
     */
    public boolean isHappy(int number) {
        //定义set集合，用来存储sum，判断是否有重复的和
        Set<Integer> set = new HashSet<>();

        while (number != 1) {
            int sum = 0;
            while (number > 0) {
                sum = (number % 10) * (number % 10);
                if (set.contains(sum)) {
                    return false;
                } else {
                    set.add(sum);
                }
                number /= 10;
            }
            number = sum;
        }

        return true;
    }

    @Test
    public void test1() {
        int num = 19;
        boolean happy = isHappy(num);
        System.out.println(num+"是快乐数字吗？");
        System.out.println(happy?"是":"不是");
    }
}
