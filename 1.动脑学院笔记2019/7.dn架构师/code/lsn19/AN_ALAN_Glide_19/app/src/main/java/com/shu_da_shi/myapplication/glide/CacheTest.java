package com.shu_da_shi.myapplication.glide;

import com.shu_da_shi.myapplication.glide.recycle.Resource;

public class CacheTest  implements Resource.ResourceListener, MemoryCache.ResourceRemoveListener{
    private LruMemoryCache lruMenoryCache;
    private ActiveResource activeResource;

    public Resource test(Key key){
        //内存缓存
        lruMenoryCache = new LruMemoryCache(10);
        lruMenoryCache.setResourceRemoveListener(this);

        //活动资源缓存
        activeResource = new ActiveResource(this);

        /**
         * 第一次 从活动资源中查找是否正在使用的图片
         */
        Resource resource = activeResource.get(key);
        if(null != resource){
            resource.acquire();
            return resource;
        }

        /**
         * 第二步 从内存缓存中查找
         */
        resource = lruMenoryCache.get(key);
        if(null != resource){
            //1. 为什么内存缓存移除？
            //因为lru可能移除此图片，也有可能recycle掉此图片
            //如果不移除，则下次使用此图片从活动资源中能找到，但是这个图片可能被recycle掉了
            resource.acquire();
            activeResource.activete(key, resource);
            lruMenoryCache.remove2(key);
            return resource;
        }
        return null;
    }

    @Override
    public void onResourceRemoved(Resource resource) {

    }

    @Override
    public void onResourceReleased(Key key, Resource resource) {

    }
}
