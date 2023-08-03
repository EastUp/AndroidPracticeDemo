package com.east.recyclerview.recycleranalysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.east.recyclerview.R;
import com.east.recyclerview.recycleranalysis.basicUse.BasicUseActivity;
import com.east.recyclerview.recycleranalysis.commonAdapterUse.CommonAdapterActivity;
import com.east.recyclerview.recycleranalysis.dragItemAnimatorUse.DragItemAnimatorActivity;
import com.east.recyclerview.recycleranalysis.headerFooterUse.HeaderFooterActivity;
import com.east.recyclerview.recycleranalysis.refreshLoad.RefreshLoadActivity;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  曾辉的RecycyelrView包
 *  @author: East
 *  @date: 2019-12-25 09:59
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class RecyclerAnalysisActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycleranalysis);
    }

    public void basicUse(View view) {
        Intent intent = new Intent(this, BasicUseActivity.class);
        startActivity(intent);
    }

    public void commonAdapter(View view) {
        Intent intent = new Intent(this, CommonAdapterActivity.class);
        startActivity(intent);
    }

    public void dragItemAnimator(View view) {
        Intent intent = new Intent(this, DragItemAnimatorActivity.class);
        startActivity(intent);
    }

    public void headerFooter(View view) {
        Intent intent = new Intent(this, HeaderFooterActivity.class);
        startActivity(intent);
    }

    public void refreshLoad(View view){
        Intent intent = new Intent(this, RefreshLoadActivity.class);
        startActivity(intent);
    }
}
