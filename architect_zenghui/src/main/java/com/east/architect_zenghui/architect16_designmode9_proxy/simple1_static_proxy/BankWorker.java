package com.east.architect_zenghui.architect16_designmode9_proxy.simple1_static_proxy;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  代理对象 - 银行工作人员 静态代理,代理的是类,一开始就知道代理的内容
 *  @author: East
 *  @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class BankWorker implements IBank {

    private Man man;

    /**
     * 持有被代理的对象
     * @param man
     */
    public BankWorker(Man man) {
        this.man = man;
    }

    @Override
    public void applyBank() {
        System.out.println("开始受理");
        man.applyBank();
        System.out.println("操作完毕");
    }


    /**
     *  如果每增加一个方法,就添加代理,那么太麻烦了
     */
    @Override
    public void lostBank() {
        System.out.println("开始受理");
        man.lostBank();
        System.out.println("操作完毕");
    }
}
