package com.east.connotationjokes.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.east.connotationjokes.MessageAIDL;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 跨进程的Service
 *  @author: jamin
 *  @date: 2020/5/24
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class MessageService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    private MessageAIDL.Stub mIBinder = new MessageAIDL.Stub() {
        @Override
        public String getUserName() throws RemoteException {
            return "eastrisewm@163.com";
        }

        @Override
        public String getPwd() throws RemoteException {
            return "19941230";
        }
    };
}
