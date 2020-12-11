package com.example.administrator.lsn6_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //decodeResourcer 解码控制参数
        //opts.inDensity表示像素密度   根据drawable目录进行计算
        //opts.inTargetDensity   画到屏幕上的像素密度
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.wyz_p);
        i(bitmap);
        //80*80
        Bitmap bitmap2=ImageResize.resizeBitmap(getApplicationContext(),R.mipmap.wyz_p,373,459,false);
        i(bitmap2);
    }

    void i(Bitmap bitmap){
        Log.i("jett","图片"+bitmap.getWidth()+"X"+bitmap.getHeight()+" 内存大小:"+bitmap.getByteCount());
    }
}








