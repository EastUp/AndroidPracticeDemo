package com.shu_da_shi.myapplication.glide.cache;

import com.shu_da_shi.myapplication.glide.cache.recycle.Resource;

public interface MemoryCache {

    interface ResourceRemoveListener{
        void onResourceRemoved(Resource resource);
    }

    void setResourceRemoveListener(ResourceRemoveListener resourceRemoveListener);



    Resource put(Key key, Resource resource);

    Resource remove2(Key key);
}
