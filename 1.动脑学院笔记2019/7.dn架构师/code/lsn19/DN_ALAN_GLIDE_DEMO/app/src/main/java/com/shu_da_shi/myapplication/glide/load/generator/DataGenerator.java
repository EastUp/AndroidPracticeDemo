package com.shu_da_shi.myapplication.glide.load.generator;

import com.shu_da_shi.myapplication.glide.cache.Key;

public interface DataGenerator {
    interface DataGeneratorCallback {

        enum DataSource {
            REMOTE,
            CACHE
        }

        void onDataReady(Key sourceKey, Object data, DataSource dataSource);

        void onDataFetcherFailed(Key sourceKey, Exception e);
    }

    boolean startNext();

    void cancel();
}
