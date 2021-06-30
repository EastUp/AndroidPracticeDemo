package com.shu_da_shi.myapplication.glide.cache;

import com.shu_da_shi.myapplication.glide.cache.recycle.BitmapPool;
import com.shu_da_shi.myapplication.glide.cache.recycle.LruBitmapPool;
import com.shu_da_shi.myapplication.glide.cache.recycle.Resource;

public class CacheTest  implements Resource.ResourceListener, MemoryCache.ResourceRemoveListener{
    private LruMemoryCache lruMenoryCache;
    private ActiveResource activeResource;
    private BitmapPool bitmapPool;

    public Resource test(Key key){
        bitmapPool = new LruBitmapPool(10);
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


    /**
     * 从内存缓存被动移除   回调放入 复用池
     * @param resource
     */
    @Override
    public void onResourceRemoved(Resource resource) {
        bitmapPool.put(resource.getBitmap());
    }

    /**
     * 这个资源没有正在使用了
     * 将其从活动资源移除
     * 重新加入到内存缓存中
     * @param key
     * @param resource
     */
    @Override
    public void onResourceReleased(Key key, Resource resource) {
        activeResource.deactivete(key);
        lruMenoryCache.put(key, resource);
    }
}
