package com.example.administrator.lsn6_demo;

import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

public class Singleton {
    private static Singleton singleton;
//    private Callback callback;
    private WeakReference<Callback> callback;
    public static Singleton getInstance(){
        if(singleton==null){
            singleton=new Singleton();
        }
        return singleton;
    }



    public void setCallback(Callback callback){
        this.callback=new WeakReference<>(callback);
    }
    public Callback getCallback(){
        return callback.get();
    }
    public interface Callback{
        void callback();
    }
}
