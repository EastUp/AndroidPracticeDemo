package com.east.architect_zenghui.architect12_Designmode5_tempolate;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 线程池测试
 *  @author: East
 *  @date: 2020-02-19
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class ThreadPoolTest {
    static ThreadPoolExecutor threadPoolExecutor;

    private final static BlockingQueue<Runnable> mPoolWorkQueue =
            new LinkedBlockingQueue<>(128);

    static {
        threadPoolExecutor = new ThreadPoolExecutor(
                4,    // 核心线程数
                10, // 最大线程数   非核心线程创建是在,核心线程调用完,任务队列已经满的时候才会创建
                60, // 线程存活的时间，没事干的时候的空闲存活时间，超过这个时间线程就会被销毁
                TimeUnit.SECONDS, // 线程存活时间的单位
                mPoolWorkQueue, // 线程队列
                new ThreadFactory() { // 线程创建工厂，如果线程池需要创建线程就会调用 newThread 来创建
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r/*, "自己创建的线程名字"*/);
                        thread.setDaemon(false); // 不是守护线程
                        return thread;

                    }
                });

    }

    public static void main(String[] args) {
        for (int i=0;i<20;i++){
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("下载图片显示完毕"+Thread.currentThread().getName());
                }
            };
            // 加入线程队列，寻找合适的时机去执行
            threadPoolExecutor.execute(r);
        }
    }

}
