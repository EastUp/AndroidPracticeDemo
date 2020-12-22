package com.dn.deamon;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.dn.deamon.activity.KeepManager;
import com.dn.deamon.jobscheduler.MyJobService;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Activity提权
//        KeepManager.getInstance().registerKeepReceiver(getApplicationContext());
        // service提权
//        startService(new Intent(this, ForegroundService.class));
        // stickyService拉活
//        startService(new Intent(this, StickyService.class));
        //账户同步拉活
//        AccountHelper.addAccount(this);
//        AccountHelper.autoSync();
        //JobScheduler拉活
        MyJobService.startJob(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeepManager.getInstance().unRegisterKeepReceiver(getApplicationContext());
    }
}
