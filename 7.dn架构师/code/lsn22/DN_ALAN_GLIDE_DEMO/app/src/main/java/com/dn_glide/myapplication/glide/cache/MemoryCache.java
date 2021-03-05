package com.dn_glide.myapplication.glide.cache;

import com.dn_glide.myapplication.glide.cache.recycle.Resource;

public interface MemoryCache {

    void clearMemory();

    void trimMemory(int level);

    interface ResourceRemoveListener{
        void onResourceRemoved(Resource resource);
    }

    Resource put(Key key, Resource resource);

    void setResourceRemoveListener(ResourceRemoveListener listener);

    Resource remove2(Key key);

}
