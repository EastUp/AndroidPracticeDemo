package com.dn_glide.myapplication.glide.load.generator;

import com.dn_glide.myapplication.glide.cache.Key;

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
