package com.shu_da_shi.myapplication.glide.cache;


import com.shu_da_shi.myapplication.glide.recycle.Resource;

public interface MemoryCache {

    interface ResourceRemovedListener {
        void onResourceRemoved(Resource resource);
    }


    Resource remove2(Key key);


    Resource put(Key key, Resource resource);


    void setResourceRemovedListener(ResourceRemovedListener listener);

    void clearMemory();

    void trimMemory(int level);
}
