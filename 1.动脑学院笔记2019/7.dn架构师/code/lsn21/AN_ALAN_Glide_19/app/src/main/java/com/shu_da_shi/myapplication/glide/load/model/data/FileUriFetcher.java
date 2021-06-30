package com.shu_da_shi.myapplication.glide.load.model.data;

import android.content.ContentResolver;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//file://
public class FileUriFetcher implements DataFetcher<InputStream>{
    private final Uri uri;
    private final ContentResolver cr;

    public FileUriFetcher(Uri uri, ContentResolver cr) {
        this.uri = uri;
        this.cr = cr;
    }

    @Override
    public void loadData(DataFetcherCallBack<InputStream> callBack) {
        InputStream is = null;
        try {
            is = cr.openInputStream(uri);
            callBack.onFetcherReady(is);
        } catch (FileNotFoundException e) {
            callBack.onLoadFailed(e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void cancel() {

    }
}
