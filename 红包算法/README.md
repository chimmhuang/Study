# 微信红包分配
## 学习资源出处：https://github.com/crossoverJie/JCSprout
>该算法可以生成一个指定的随机红包列表。

### 生成红包的主流程
>```java
>/**
>  * 红包分配
>  * @param money 总金额
>  * @param count 分配数量
>  * @return 红包集合
>  */
>  public List<Integer> splitRedPacket(int money, int count)
>```
```flow
st=>start: 输入金额和红包个数
splitRedPacket=>operation: 分配红包金额
moneyCheck=>condition: if (MAX_MONEY * count <= money)
(最大红包 * 个数 <= 总金额)？
moneyError=>end: 提示用户，最大一人只能收取200元红包
countMaxRedPacket=>operation: 计算单人最大获取红包
最大红包为平均值的2.1倍
若超过了200元，则最大红包设置为200元
loopToCreateRedPacket=>operation: 开始for循环生成红包列表
内部调用randomRedPacket方法生成随机红包金额
redPacketCountCheck=>condition: for (int i = 0; i < count; i++)
是否退出循环？
returnRedPacketList=>end: 返回红包列表

st->splitRedPacket->moneyCheck->cond
moneyCheck(no)->moneyError
moneyCheck(yes)->countMaxRedPacket
countMaxRedPacket->loopToCreateRedPacket->redPacketCountCheck
redPacketCountCheck(no)->loopToCreateRedPacket
redPacketCountCheck(yes)->returnRedPacketList
```

### 随机生成红包金额
>```java
>/**
>  * 随机红包
>  * @param totalMoney 总金额
>  * @param minMoney   最小金额
>  * @param maxMoney   最大金额
>  * @param count      红包数量
>  * @return int:红包金额
>  */
>  private int randomRedPacket(int totalMoney, int minMoney, int maxMoney, int count)
>```

```flow
st=>start: randomRedPacket
fun=>operation: 传入[总金额][最小金额][最大金额][红包数量]
check=>condition: 如果红包个数只有1个,
则返回总金额
如果最小金额和最大金额相等,
则返回该金额
checkFalse=>end: 返回对应的金额
checkMaxMoney=>operation: 如果最大金额大于了剩余金额
则用剩余金额
因为这个 money 每分配一次都会减小
maxMoney = maxMoney > totalMoney ?
totalMoney : maxMoney;
createRedPacket=>operation: 生成随机红包（精华）
计算剩余红包金额
校验剩余的金额的平均值是否在 最小值和最大值这个范围内
checkMoney(int lastMoney, int count)
isLessThanMinMoney=>condition: 平均值>=最小金额？
returnLESS=>end: 返回小于的状态码（-1）
isGreaterThanMaxMoney=>condition: 平均值<=最大金额？
returnMORE=>operation: 返回大于的状态吗（-2）
returnOK=>operation: 返回OK状态码（1）
checkRedpacket=>condition: 如果OK状态码:返回红包金额，
如果LESS或MORE状态码:递归计数+1，
递归调用该方法
returnRedPacket=>end: 返回红包金额

st->fun->check
check(no)->checkFalse
check(yes)->checkMaxMoney->createRedPacket->isLessThanMinMoney
isLessThanMinMoney(no)->returnLESS
isLessThanMinMoney(yes)->isGreaterThanMaxMoney
isGreaterThanMaxMoney(no)->returnMORE
isGreaterThanMaxMoney(yes)->returnOK->checkRedpacket
checkRedpacket(no)->fun
checkRedpacket(yes)->returnRedPacket
```

### 源码
```java
package com.solomon.red;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Created by solomon on 2019/1/7 0007.
 * <p>
 * 模拟微信红包生成（单位：分）
 */
public class RedPacket {

    /**
     * 生成红包最小值 1分
     */
    private static final int MIN_MONEY = 1;

    /**
     * 生成红包最大值 200人民币
     */
    private static final int MAX_MONEY = 200 * 100;

    /**
     * 小于最小值
     */
    private static final int LESS = -1;
    /**
     * 大于最大值
     */
    private static final int MORE = -2;

    /**
     * 正常值
     */
    private static final int OK = 1;

    /**
     * 最大的红包是平均值的 TIMES 倍，防止某一次分配红包较大
     */
    private static final double TIMES = 2.1F;

    /**
     * 递归计数器
     */
    private int recursiveCount = 0;

    /**
     * 红包分配
     *
     * @param money 总金额
     * @param count 分配数量
     * @return
     */
    public List<Integer> splitRedPacket(int money, int count) {
        List<Integer> moneys = new LinkedList<>();

        //金额检查，如果最大红包 * 个数 <= 总金额;则需要调大最小红包 MAX_MONEY
        if (MAX_MONEY * count <= money) {
            System.err.println("请调大最小红包金额 MAX_MONEY=[" + MAX_MONEY + "]");
            return moneys;
        }

        //计算出最大红包
        int max = (int) ((money / count) * TIMES);
        max = max > MAX_MONEY ? MAX_MONEY : max;

        for (int i = 0; i < count; i++) {
            //随机获取红包
            int redPacket = randomRedPacket(money, MIN_MONEY, max, count - i);
            moneys.add(redPacket);
            //总金额每次减少
            money -= redPacket;
        }
        return moneys;
    }

    /**
     * 随机红包
     *
     * @param totalMoney 总金额
     * @param minMoney   最小金额
     * @param maxMoney   最大金额
     * @param count      红包数量
     * @return
     */
    private int randomRedPacket(int totalMoney, int minMoney, int maxMoney, int count) {
        //只有一个红包直接返回
        if (count == 1) {
            return totalMoney;
        }
        if (minMoney == maxMoney) {
            return minMoney;
        }

        //如果最大金额大于了剩余金额 则用剩余金额 因为这个 money 每分配一次都会减小
        maxMoney = maxMoney > totalMoney ? totalMoney : maxMoney;

        //在minMoney到maxMoney 生成一个随机红包
        int redPacket = (int) (Math.random() * (maxMoney - minMoney) + minMoney);

        int lastMoney = totalMoney - redPacket;

        int status = checkMoney(lastMoney, count - 1);

        //正常金额
        if (OK == status) {
            return redPacket;
        }

        //如果生成的急呢不合法，则重新递归生成
        if (LESS == status) {
            recursiveCount++;
            System.out.println("recursiveCount==" + recursiveCount);
            return randomRedPacket(totalMoney, minMoney, redPacket, count);
        }

        if (MORE == status) {
            recursiveCount++;
            System.out.println("recursiveCount==" + recursiveCount);
            return randomRedPacket(totalMoney, redPacket, maxMoney, count);
        }

        return redPacket;
    }


    /**
     * 校验剩余的金额的平均值是否在 最小值和最大值这个范围内
     *
     * @param lastMoney
     * @param count
     * @return
     */
    private int checkMoney(int lastMoney, int count) {
        double avg = lastMoney / count;
        if (avg < MIN_MONEY) {
            return LESS;
        }

        if (avg > MAX_MONEY) {
            return MORE;
        }

        return OK;
    }


    public static void main(String[] args) {
        RedPacket redPacket = new RedPacket();
        List<Integer> redPackets = redPacket.splitRedPacket(2, 2);
        System.out.println(redPacket);

        int sum = 0;
        for (Integer red : redPackets) {
            sum += red;
            System.out.println("你抢到了：" + red / (100*1.0) + "元");
        }
        System.out.println(sum);
    }
}
```

