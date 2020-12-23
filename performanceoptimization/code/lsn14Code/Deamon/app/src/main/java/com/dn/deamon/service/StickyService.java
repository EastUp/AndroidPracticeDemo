package com.dn.deamon.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @author Damon
 * @Date 2019/5/30 23:38
 */
public class StickyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 只要 targetSdkVersion 不小于5，就默认是 START_STICKY。
        // 但是某些ROM 系统不会拉活。
        // 并且经过测试，Service 第一次被异常杀死后很快被重启，第二次会比第一次慢，第三次又会比前一次慢，
        // 一旦在短时间内 Service 被杀死4-5次，则系统不再拉起。
        return super.onStartCommand(intent, flags, startId);
    }
}
