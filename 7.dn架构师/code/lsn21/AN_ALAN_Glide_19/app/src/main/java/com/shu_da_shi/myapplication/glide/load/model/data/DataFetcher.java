package com.shu_da_shi.myapplication.glide.load.model.data;

/**
 * 负责数据获取
 */
public interface DataFetcher<Data> {
    interface DataFetcherCallBack<Data> {
        // 数据加载完成
        void onFetcherReady(Data data);

        //加载失败
        void onLoadFailed(Exception e);
    }

    void loadData(DataFetcherCallBack<Data> callBack);

    void cancel();
}
