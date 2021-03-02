package com.shu_da_shi.myapplication.glide.load.model;

import android.net.Uri;

import com.shu_da_shi.myapplication.glide.Key;
import com.shu_da_shi.myapplication.glide.load.Objectkey;
import com.shu_da_shi.myapplication.glide.load.model.data.DataFetcher;
import com.shu_da_shi.myapplication.glide.load.model.data.HttpUriFetcher;

import java.io.InputStream;

public class HttpUriLoader implements ModelLoader<Uri, InputStream> {
    @Override
    public boolean handles(Uri uri) {
//        Uri.fromFile("")
        String scheme = uri.getScheme();
        return scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https");
    }

    //创建加载的数据方式
    @Override
    public LoadData<InputStream> buildData(Uri uri) {

        return new LoadData<InputStream>(new Objectkey(uri), new HttpUriFetcher(uri));
    }
}
