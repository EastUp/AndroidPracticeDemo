package com.east.architect_zenghui.architect13_Designmode6_strategy.simple1;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 一般写法,有问题
 *  @author: East
 *  @date: 2020-02-21
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class FinanceManager {

    public enum Finance{
        ZHI_FU_BAO,REN_ZHONG
    }

    private float zhifubaoFinance(int month,int money){
        if (month == 3) {
            return money + money * 0.047f / 12 * month;
        }
        if (month == 6) {
            return money + money * 0.05f / 12 * month;
        }
        if (month == 12) {
            return money + money * 0.06f / 12 * month;
        }
        throw new IllegalArgumentException("月份不对");
    }

    private float renzhongFinance(int month, int money) {
        if (month == 3) {
            return money + money * 0.09f / 12 * month;
        }
        if (month == 6) {
            return money + money * 0.112f / 12 * month;
        }
        if (month == 12) {
            return money + money * 0.12f / 12 * month;
        }
        throw new IllegalArgumentException("月份不对");
    }


    /**
     * 获取计算金额
     * @param month
     * @param money
     * @return
     */
    public float finance(Finance finance,int month, int money) {
        switch (finance){
            case REN_ZHONG:
                return renzhongFinance(month,money);
            case ZHI_FU_BAO:
                return zhifubaoFinance(month,money);
            default:
                return 0f;
        }
    }
}
