package com.dn_glide.myapplication.glide.load.model.data;


/**
 * 负责数据获取
 */
public interface DataFetcher<Data> {



    interface DataFetcherCallback<Data> {
        /**
         * 数据加载完成
         */
        void onFetcherReady(Data data);

        /**
         * 加载失败
         *
         * @param e
         */
        void onLoadFaled(Exception e);
    }

    void loadData(DataFetcherCallback<? super Data> callback);

    void cancel();

    Class<?> getDataClass();

}
