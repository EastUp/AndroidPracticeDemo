package com.dn_glide.myapplication.glide.cache.recycle;

import android.graphics.Bitmap;
public interface BitmapPool {

    void put(Bitmap bitmap);

    /**
     * 获得一个可复用的Bitmap
     *  三个参数计算出 内存大小
     * @param width
     * @param height
     * @param config
     * @return
     */
    Bitmap get(int width,int height,Bitmap.Config config);


    void clearMemory();
    void trimMemory(int level);
}
