package com.shu_da_shi.myapplication.glide;

import android.app.ActivityManager;
import android.content.Context;
import android.util.DisplayMetrics;

import com.shu_da_shi.myapplication.glide.cache.ArrayPool;
import com.shu_da_shi.myapplication.glide.cache.DiskCache;
import com.shu_da_shi.myapplication.glide.cache.DiskLruCacheWrapper;
import com.shu_da_shi.myapplication.glide.cache.LruArrayPool;
import com.shu_da_shi.myapplication.glide.cache.LruResourceCache;
import com.shu_da_shi.myapplication.glide.cache.MemoryCache;
import com.shu_da_shi.myapplication.glide.load.Engine;
import com.shu_da_shi.myapplication.glide.load.GlideExecutor;
import com.shu_da_shi.myapplication.glide.manager.RequestManagerRetriever;
import com.shu_da_shi.myapplication.glide.recycle.BitmapPool;
import com.shu_da_shi.myapplication.glide.recycle.LruBitmapPool;
import com.shu_da_shi.myapplication.glide.request.RequestOptions;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * A builder class for setting default structural classes for Glide to use.
 */
public final class GlideBuilder {
    Engine engine;
    BitmapPool bitmapPool;
    MemoryCache memoryCache;
    ThreadPoolExecutor executor;
    DiskCache diskCache;
    RequestOptions defaultRequestOptions = new RequestOptions();
    ArrayPool arrayPool;

    public GlideBuilder setBitmapPool(BitmapPool bitmapPool) {
        this.bitmapPool = bitmapPool;
        return this;
    }


    public GlideBuilder setMemoryCache(MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
        return this;
    }

    public GlideBuilder setArrayPool(ArrayPool arrayPool) {
        this.arrayPool = arrayPool;
        return this;
    }


    public GlideBuilder setDiskCache(DiskCache diskCache) {
        this.diskCache = diskCache;
        return this;
    }

    public GlideBuilder setDefaultRequestOptions(RequestOptions defaultRequestOptions) {
        this.defaultRequestOptions = defaultRequestOptions;
        return this;
    }

    public GlideBuilder setExecutor(ThreadPoolExecutor service) {
        this.executor = service;
        return this;
    }



    private static int getMaxSize(ActivityManager activityManager) {
        //???????????????????????????0.4??????????????????
        final int memoryClassBytes = activityManager.getMemoryClass() * 1024 * 1024;
        return Math.round(memoryClassBytes * 0.4f);
    }

    public Glide build(Context context) {
        context = context.getApplicationContext();
        if (executor == null) {
            executor = GlideExecutor.newExecutor();
        }

        if (arrayPool == null) {
            arrayPool = new LruArrayPool();
        }

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        int maxSize = getMaxSize(activityManager);

        //??????????????????????????????????????????
        int availableSize = maxSize - arrayPool.getMaxSize();

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        // ???????????????????????????argb?????????????????????
        int screenSize = widthPixels * heightPixels * 4;

        //bitmap????????? 4???
        float bitmapPoolSize = screenSize * 4.0f;
        //??????????????? 2???
        float memoryCacheSize = screenSize * 2.0f;

        if (bitmapPoolSize + memoryCacheSize <= availableSize) {
            bitmapPoolSize = Math.round(bitmapPoolSize);
            memoryCacheSize = Math.round(memoryCacheSize);
        } else {
            //?????????????????? 6???
            float part = availableSize / 6.0f;
            bitmapPoolSize = Math.round(part * 4);
            memoryCacheSize = Math.round(part * 2);
        }

        if (bitmapPool == null) {
            bitmapPool = new LruBitmapPool((int) bitmapPoolSize);
        }

        if (memoryCache == null) {
            memoryCache = new LruResourceCache((int) memoryCacheSize);
        }

        if (diskCache == null) {
            diskCache = new DiskLruCacheWrapper(context);
        }

        if (engine == null) {
            engine = new Engine(context);
        }
        memoryCache.setResourceRemovedListener(engine);

        RequestManagerRetriever requestManagerRetriever =
                new RequestManagerRetriever();

        return new Glide(context, requestManagerRetriever, this);
    }
}
