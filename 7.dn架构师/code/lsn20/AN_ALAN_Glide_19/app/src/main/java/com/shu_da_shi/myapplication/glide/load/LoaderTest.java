package com.shu_da_shi.myapplication.glide.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.shu_da_shi.myapplication.glide.load.model.HttpUriLoader;
import com.shu_da_shi.myapplication.glide.load.model.ModelLoader;
import com.shu_da_shi.myapplication.glide.load.model.data.DataFetcher;

import java.io.InputStream;

public class LoaderTest {

    private void test(Context context){
        Uri uri  = Uri.parse("http://www.xxxxx.xxx");
        HttpUriLoader httpUriLoader = new HttpUriLoader();
        ModelLoader.LoadData<InputStream> inputStreamLoadData = httpUriLoader.buildData(uri);
        inputStreamLoadData.fetcher.loadData(new DataFetcher.DataFetcherCallBack<InputStream>() {
            @Override
            public void onFetcjerReady(InputStream inputStream) {
                //解析字节输入流，获取一张图片
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            }

            @Override
            public void onLoadFailed(Exception e) {

            }
        });
    }
}
