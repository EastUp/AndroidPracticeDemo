package com.example.administrator.lsn_9_demo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;

/**
 * 主动得到充电状态和网络状态
 */
public class Battery {
    /**
     * 是否在充电
     */
    public static boolean isPlugged(Context context){
        //发送一个包含充电状态的广播,并用粘性广播
        IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = context.registerReceiver(null, filter);
        //获取充电状态
        int isPlugged=intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);

        boolean acPlugged=isPlugged==BatteryManager.BATTERY_PLUGGED_AC;
        boolean usbPlugged=isPlugged==BatteryManager.BATTERY_PLUGGED_USB;
        boolean wrieliessPlugged=isPlugged==BatteryManager.BATTERY_PLUGGED_WIRELESS;

        return acPlugged || usbPlugged || wrieliessPlugged;
    }

    /**
     * 是否在使用wifi
     */
    public static boolean isWifi(Context context){
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取当前活动的网络信息
        NetworkInfo activeNetworkInfo=cm.getActiveNetworkInfo();
        if(null!=activeNetworkInfo && activeNetworkInfo.isConnected()
                && activeNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return false;
    }
}








