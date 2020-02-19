package com.east.architect_zenghui.architect12_Designmode5_tempolate;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: Activity的基类,使用模板设计模式
 *  @author: East
 *  @date: 2020-02-19
 * |---------------------------------------------------------------------------------------------------------------|
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1.设置布局
        setContentView();
        // 写一些公用的方法，ButterKnife注解，统一管理Activity，等等
        // 2. 初始化Title
        initTitle();
        // 3. 初始化View
        initView();
        // 4. 访问接口数据（initData）
        initData(savedInstanceState);
    }

    protected abstract void setContentView();

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);

    //写一大堆方法 ，很多 Activity 都要用的
    public void startActivity(Class<? extends BaseActivity> clazz){
        startActivity(new Intent(this,clazz));
    }

}

