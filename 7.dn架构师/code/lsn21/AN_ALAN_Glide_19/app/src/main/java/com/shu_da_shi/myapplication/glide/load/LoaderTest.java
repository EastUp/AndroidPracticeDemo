package com.shu_da_shi.myapplication.glide.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.shu_da_shi.myapplication.R;
import com.shu_da_shi.myapplication.glide.load.model.FileUriLoader;
import com.shu_da_shi.myapplication.glide.load.model.HttpUriLoader;
import com.shu_da_shi.myapplication.glide.load.model.ModelLoader;
import com.shu_da_shi.myapplication.glide.load.model.ModelLoaderRegistry;
import com.shu_da_shi.myapplication.glide.load.model.StringModelLoader;
import com.shu_da_shi.myapplication.glide.load.model.data.DataFetcher;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class LoaderTest {

    private void test(Context context){
        ModelLoaderRegistry modelLoaderRegistry = new ModelLoaderRegistry();
        modelLoaderRegistry.add(String.class, InputStream.class, new StringModelLoader.Factory());
        modelLoaderRegistry.add(Uri.class, InputStream.class, new HttpUriLoader.Factory());
        modelLoaderRegistry.add(Uri.class, InputStream.class, new FileUriLoader.Factory(context.getContentResolver()));


        List<ModelLoader<String, ?>> modelLoaders = modelLoaderRegistry.getModelLoaders(String.class);
        ModelLoader<String, ?> stringModelLoader = modelLoaders.get(0);
        final ModelLoader.LoadData<InputStream> loadData = (ModelLoader.LoadData<InputStream>) stringModelLoader.buildData("https://ss1.bdstatic" +
                ".com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2669567003," +
                "3609261574&fm=27&gp=0.jpg22222222asads");

        new Thread(){
            @Override
            public void run() {
                loadData.fetcher.loadData(new DataFetcher.DataFetcherCallBack<InputStream>() {
                    @Override
                    public void onFetcherReady(InputStream o) {

                    }

                    @Override
                    public void onLoadFailed(Exception e) {

                    }
                });
            }
        }.start();

//        Uri uri  = Uri.parse("http://www.xxxxx.xxx");
//        HttpUriLoader httpUriLoader = new HttpUriLoader();
//        ModelLoader.LoadData<InputStream> inputStreamLoadData = httpUriLoader.buildData(uri);
//        inputStreamLoadData.fetcher.loadData(new DataFetcher.DataFetcherCallBack<InputStream>() {
//            @Override
//            public void onFetcherReady(InputStream inputStream) {
//                //解析字节输入流，获取一张图片
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            }
//
//            @Override
//            public void onLoadFailed(Exception e) {
//
//            }
//        });

//        Uri uri1 = Uri.fromFile(new File("sd/xxx.png"));  //file://
//        FileUriLoader loader = new FileUriLoader(context.getContentResolver());
//        ModelLoader.LoadData<InputStream> inputStreamLoadData1 = loader.buildData(uri);
//        inputStreamLoadData1.fetcher.loadData();


//        Glide.with(this).load("https://ss1.bdstatic" +
//                ".com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2669567003," +
//                "3609261574&fm=27&gp=0.jpg22222222asads")
//                .apply(new RequestOptions().error(R.drawable.ic_launcher_background).placeholder
//                        (R.mipmap.ic_launcher).override(500, 500))
//                .into(iv);
//
//
//        Glide.with(this).load(Environment.getExternalStorageDirectory()+"/main.jpg")
//                .into(iv1);
//        Glide.with(this).load(new File(Environment.getExternalStorageDirectory()+"/zyx.jpg")).into(iv2);
//
//        String str = "https://ss1.bdstatic";
//        if("http"){
//            HttpUriLoader.Factory
//        } else if("/"){
//            StringModelLoader.
//        }
//        Uri uri = Uri.fromFile(new File("/sdcard/xx.png"));
//        if("file://"){
//            FileUriLoader
//        }

    }
}
