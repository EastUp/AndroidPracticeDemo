package com.east.architect_zenghui.architect17_designmode10_prototype.simple6_intent_clone;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.east.architect_zenghui.R;


public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 三个参数传递到 Activity 2

        Intent intent = new Intent(this,Activity2.class);

        intent.putExtra("Params1","Params1");
        intent.putExtra("Params2","Params2");
        intent.putExtra("Params3","Params3");

        startActivity(intent);
    }
}
