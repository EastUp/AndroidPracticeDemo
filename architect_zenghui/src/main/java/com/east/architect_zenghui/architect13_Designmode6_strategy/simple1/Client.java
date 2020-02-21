package com.east.architect_zenghui.architect13_Designmode6_strategy.simple1;

public class Client {
    public static void main(String[] args){
        FinanceManager financeManager = new FinanceManager();
        float money = financeManager.finance(FinanceManager.Finance.REN_ZHONG,3,10000);
        System.out.println("money = "+money);
    }
}
