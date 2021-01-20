package com.east.baselibrary.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.east.baselibrary.ioc.JaminIoc

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: Activity的基类
 *  @author: jamin
 *  @date: 2020/4/30
 * |---------------------------------------------------------------------------------------------------------------|
 */
abstract class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        //加入自己的IOC注解
        JaminIoc.bind(this)
        initTitle()//初始化标题
        initView()//初始化界面
        initData()//初始化数据
    }

    //获取布局文件的ID
    protected abstract fun getLayoutId(): Int

    //初始化标题
    protected abstract fun initTitle()

    //初始化界面
    protected open fun initView(){}

    //初始化数据
    protected abstract fun initData()

    protected fun startActivity(clazz:Class<*>){
        startActivity(Intent(this,clazz))
    }

}