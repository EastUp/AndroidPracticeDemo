package com.shu_da_shi.myapplication.glide;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.shu_da_shi.myapplication.glide.cache.ArrayPool;
import com.shu_da_shi.myapplication.glide.cache.DiskCache;
import com.shu_da_shi.myapplication.glide.cache.MemoryCache;
import com.shu_da_shi.myapplication.glide.load.Engine;
import com.shu_da_shi.myapplication.glide.load.codec.StreamBitmapDecoder;
import com.shu_da_shi.myapplication.glide.load.model.FileLoader;
import com.shu_da_shi.myapplication.glide.load.model.HttpUriLoader;
import com.shu_da_shi.myapplication.glide.load.model.StringLoader;
import com.shu_da_shi.myapplication.glide.load.model.UriFileLoader;
import com.shu_da_shi.myapplication.glide.manager.RequestManagerRetriever;
import com.shu_da_shi.myapplication.glide.recycle.BitmapPool;
import com.shu_da_shi.myapplication.glide.request.RequestOptions;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.ThreadPoolExecutor;

public class Glide implements ComponentCallbacks2 {
    private static volatile Glide glide;

    private final Engine engine;
    private final BitmapPool bitmapPool;
    private final MemoryCache memoryCache;
    private final DiskCache diskCache;
    private final RequestManagerRetriever requestManagerRetriever;
    private final Context context;
    private final RequestOptions defaultRequestOptions;
    private final ThreadPoolExecutor executor;
    private final Registry registry;
    private final ArrayPool arrayPool;

    Glide(Context context, RequestManagerRetriever requestManagerRetriever, GlideBuilder
            glideBuilder) {
        this.context = context.getApplicationContext();
        this.requestManagerRetriever = requestManagerRetriever;
        this.engine = glideBuilder.engine;
        this.bitmapPool = glideBuilder.bitmapPool;
        this.memoryCache = glideBuilder.memoryCache;
        this.diskCache = glideBuilder.diskCache;
        this.defaultRequestOptions = glideBuilder.defaultRequestOptions;
        this.executor = glideBuilder.executor;
        this.arrayPool = glideBuilder.arrayPool;

        //?????????
        registry = new Registry();
        ContentResolver contentResolver = context.getContentResolver();
        registry.add(String.class, InputStream.class, new StringLoader.StreamFactory())
                .add(Uri.class, InputStream.class, new HttpUriLoader.Factory())
                .add(Uri.class, InputStream.class, new UriFileLoader.Factory(contentResolver))
                .add(File.class, InputStream.class, new FileLoader.Factory())
                .register(InputStream.class, new StreamBitmapDecoder(bitmapPool, arrayPool));
    }

    public Registry getRegistry() {
        return registry;
    }

    public static Glide get(Context context) {
        if (glide == null) {
            synchronized (Glide.class) {
                if (glide == null) {
                    checkAndInitializeGlide(context);
                }
            }
        }

        return glide;
    }

    private static void checkAndInitializeGlide(Context context) {
        initializeGlide(context, new GlideBuilder());
    }


    private static void initializeGlide(Context context, GlideBuilder builder) {
        Context applicationContext = context.getApplicationContext();
        Glide glide = builder.build(applicationContext);
        applicationContext.registerComponentCallbacks(glide);
        Glide.glide = glide;
    }


    public Engine getEngine() {
        return engine;
    }

    public RequestOptions getDefaultRequestOptions() {
        return defaultRequestOptions;
    }

    public BitmapPool getBitmapPool() {
        return bitmapPool;
    }

    public ArrayPool getArrayPool() {
        return arrayPool;
    }

    public Context getContext() {
        return context;
    }


    public MemoryCache getMemoryCache() {
        return memoryCache;
    }


    public DiskCache getDiskCache() {
        return diskCache;
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }



    public RequestManagerRetriever getRequestManagerRetriever() {
        return requestManagerRetriever;
    }


    /**
     * ???????????????????????????
     * @param context
     * @return
     */
    private static RequestManagerRetriever getRetriever(Context context) {
        return Glide.get(context).getRequestManagerRetriever();
    }

    /**
     * ?????????????????????
     * @param context ??????????????????
     * @return
     */
    public static RequestManager with(Context context) {
        return getRetriever(context).get(context);
    }

    /**
     * ?????????????????????
     * @param activity ??????????????????
     * @return
     */
    public static RequestManager with(Activity activity) {
        return getRetriever(activity).get(activity);
    }

    public static RequestManager with(View view) {
        return getRetriever(view.getContext()).get(view);
    }


    public static RequestManager with(FragmentActivity activity) {
        return getRetriever(activity).get(activity);
    }


    public static RequestManager with(android.app.Fragment fragment) {
        return getRetriever(fragment.getActivity()).get(fragment);
    }


    public static RequestManager with(Fragment fragment) {
        return getRetriever(fragment.getActivity()).get(fragment);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // Do nothing.
    }

    /**
     * ????????????
     */
    @Override
    public void onLowMemory() {
        //memory???bitmappool???????????????
        //??????memory???????????????????????????
        memoryCache.clearMemory();
        bitmapPool.clearMemory();
        arrayPool.clearMemory();
    }

    /**
     * ??????????????????
     * @param level ????????????
     */
    @Override
    public void onTrimMemory(int level) {
        memoryCache.trimMemory(level);
        bitmapPool.trimMemory(level);
        arrayPool.trimMemory(level);
    }
}
