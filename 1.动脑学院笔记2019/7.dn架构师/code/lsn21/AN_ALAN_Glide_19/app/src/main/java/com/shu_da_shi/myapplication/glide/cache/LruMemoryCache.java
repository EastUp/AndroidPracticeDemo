package com.shu_da_shi.myapplication.glide.cache;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

import com.shu_da_shi.myapplication.glide.cache.recycle.Resource;

public class LruMemoryCache extends LruCache<Key, Resource> implements MemoryCache {
    private ResourceRemoveListener listener;

    private boolean isRemoved;

    public LruMemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(@NonNull Key key, @NonNull Resource value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //当在4.4以上手机复用的使用 需要通过此函数获得占用内存
            return value.getBitmap().getAllocationByteCount();
        }
        return value.getBitmap().getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, @NonNull Key key, @NonNull Resource oldValue, @Nullable Resource newValue) {
        if (null != listener && null != oldValue && !isRemoved) {
            this.listener.onResourceRemoved(oldValue);
        }
    }

    @Override
    public void setResourceRemoveListener(ResourceRemoveListener resourceRemoveListener) {
        this.listener = resourceRemoveListener;
    }

    @Override
    public Resource remove2(Key key) {
        isRemoved = true;
        Resource remove = remove(key);
        isRemoved = false;
        return remove;
    }
}
