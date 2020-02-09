package com.east.architect_zenghui.reflect_annotation_generics;


import android.os.Bundle;
import android.widget.TextView;

import com.east.architect_zenghui.R;
import com.east.architect_zenghui.reflect_annotation_generics.java.BaseActivity;
import com.east.architect_zenghui.reflect_annotation_generics.java.ViewById;
import com.east.architect_zenghui.reflect_annotation_generics.java.ViewUtils;


public class ReflectAnnotationGenericsActivity extends BaseActivity {

    @ViewById(R.id.tv)
    private TextView tv;
    @ViewById(R.id.tv1)
    private TextView tv1;
    private int mPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect_annotation_generics);

        ViewUtils.inject(this);

        tv.setText("ViewById");

//        startActivity(ReflectAnnotationGenericsActivity.class);

        /*// 通过反射
        // AssetManager assetManager = new AssetManager();
        // Resources resources = new Resources(,null,null);

        //TestBean testBean = new TestBean();
        // 反射
        try {
            // 创建对象，很多系统的类，都被系统隐藏了，或者说只有系统可以直接 new，可以通过反射创建对象 newInstance() 调用的是无参
            *//*TestBean testBean =  TestBean.class.newInstance();
            testBean.sysName();*//*
            // getDeclaredConstructor 从所有的构造方法里面找， getConstructor  从公共构造方法里面去找
            // 获取一个参数的构造方法 而且参数是String类型
            Constructor constructor = TestBean.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);// 设置权限
            // "晴天不改" 调用构造方法传的参数 == new TestBean("晴天不改");
            TestBean testBean = (TestBean) constructor.newInstance("晴天不改");
            //testBean.sysName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 方法调用
        try {
            *//*TestBean testBean = new TestBean("Darren");
            Method method = TestBean.class.getDeclaredMethod("sysName");
            method.setAccessible(true);
            method.invoke(testBean);*//*

            AssetManager addAssetPath = AssetManager.class.newInstance();
            Method addAssetPathMethod = AssetManager.class.getDeclaredMethod("addAssetPath",String.class);// addAssetPath(String path)
            addAssetPathMethod.invoke(addAssetPath,"sdcard/dds/red.skin");
            // assetManager.addAssetPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取属性
        try {
            TestBean testBean = new TestBean("baby");
            Field field = TestBean.class.getDeclaredField("name");
            field.setAccessible(true);
            String name = (String) field.get(testBean);
            Log.e("TAG",name);

            Class clazz = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField =  clazz.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object sCurrentActivityThread = sCurrentActivityThreadField.get(null);

            Log.e("TAG",sCurrentActivityThread.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
