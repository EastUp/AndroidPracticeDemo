package com.east.architect_zenghui.architect8_designmode1_singleton.java.simple2.sync;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: volatile测试
 *  @author: East
 *  @date: 2020-02-15 14:34
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class VolatileTest {
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while(true){
            if(td.isFlag()){
                System.out.println("------------------");
                break;
            }
        }

        // 执行结果？ flag= true  ------------------
    }
}

class ThreadDemo implements Runnable {
    private volatile boolean flag = false;
    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        flag = true;
        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
