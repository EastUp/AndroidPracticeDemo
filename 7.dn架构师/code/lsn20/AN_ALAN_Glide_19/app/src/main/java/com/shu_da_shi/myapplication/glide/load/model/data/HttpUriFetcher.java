package com.shu_da_shi.myapplication.glide.load.model.data;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUriFetcher implements DataFetcher<InputStream> {

    private final Uri uri;
    //如果请求被取消
    private boolean isCanceled;

    public HttpUriFetcher(Uri uri) {
        this.uri = uri;
    }

    @Override
    public void loadData(DataFetcherCallBack callBack) {
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            is = connection.getInputStream();
            int responseCode = connection.getResponseCode();
            if (isCanceled) {
                return;
            }
            if (responseCode == HttpURLConnection.HTTP_OK) {
                callBack.onFetcjerReady(is);
            } else {
                callBack.onLoadFailed(new RuntimeException(connection.getResponseMessage()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection) {
                connection.disconnect();
            }
        }
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }
}
