package com.east.architect_zenghui.architect_31_rxjava3.rxjava;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/14
 * |---------------------------------------------------------------------------------------------------------------|
 */
public abstract class Schedulers {
    static Schedulers MAIN_THREAD;
    static Schedulers IO;
    static {
        IO = new IOSchedulers();
        MAIN_THREAD = new MainSchedulers(new Handler(Looper.getMainLooper()));
    }

    public static Schedulers io(){
        return IO;
    }

    public static Schedulers mainThread(){
        return MAIN_THREAD;
    }

    public abstract void scheduleDirect(Runnable runnable);

    private static class IOSchedulers extends Schedulers{

        private ExecutorService executorService = Executors.newScheduledThreadPool(1, r -> new Thread(r));

        @Override
        public void scheduleDirect(Runnable runnable) {
            executorService.submit(runnable);
        }
    }

    private static class MainSchedulers extends Schedulers {

        private final Handler handler;
        public MainSchedulers(Handler handler) {
           this.handler = handler;
        }

        @Override
        public void scheduleDirect(Runnable runnable) {
            Message message = Message.obtain(handler,runnable);
            message.sendToTarget();
        }
    }
}
