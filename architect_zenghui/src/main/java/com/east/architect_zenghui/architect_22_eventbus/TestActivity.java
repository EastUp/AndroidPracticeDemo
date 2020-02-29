package com.east.architect_zenghui.architect_22_eventbus;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.east.architect_zenghui.R;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  EventBus测试
 *  @author: East
 *  @date: 2020/2/29 1:41 PM
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        findViewById(R.id.test_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("text");
            }
        });
    }
}
