package com.east.connotationjokes;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.east.east_utils.utils.ToastUtils;
import com.east.framelibrary.BaseSkinActivity;
import com.east.framelibrary.skin.SkinManager;
import com.east.permission.PermissionCheckUtils;
import com.east.permission.PermissionListener;

import java.io.File;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 测试IOC框架
 *  @author: jamin
 *  @date: 2020/4/28
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class MainActivity extends BaseSkinActivity {

    private MessageAIDL messageAIDL;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messageAIDL = MessageAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messageAIDL = null;
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {
        PermissionCheckUtils.INSTANCE.checkPermission(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, new PermissionListener() {
            @Override
            public void onCancel() {
                finish();
            }
        });

        Intent intent = new Intent();
        intent.setAction("com.east.aidl.meesageservice");
        intent.setPackage(getPackageName()); //必须要这么设置,需要显示的指定Intent
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    public void skin(View view){
        // 从服务器上下载

        String SkinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                +File.separator +"red.skin";
        // 换肤
        SkinManager.getInstance().loadSkin(SkinPath);
    }

    public void skin1(View view){
        // 恢复默认
        SkinManager.getInstance().restoreDefault();
    }


    public void skin2(View view){
        // 跳转
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void getUser(View view){
        try {
            ToastUtils.show(messageAIDL.getUserName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getPwd(View view){
        try {
            ToastUtils.show(messageAIDL.getPwd());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
