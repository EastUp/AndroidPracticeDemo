package com.dn_glide.myapplication.glide.load;

import com.dn_glide.myapplication.glide.cache.Key;

import java.security.MessageDigest;

public class ObjectKey implements Key {

    private final Object object;

    public ObjectKey(Object object) {
        this.object = object;
    }

    /**
     * 当磁盘缓存的时候 key只能是字符串
     * ObjectKey变成一个字符串
     * 序列化:json
     * <p>
     * 将ObjectKey转变成一个字符串的手段
     *
     * @param md md5/sha1
     */
    @Override
    public void updateDiskCacheKey(MessageDigest md) {
        md.update(getKeyBytes());
    }

    @Override
    public byte[] getKeyBytes() {
        return object.toString().getBytes();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectKey objectKey = (ObjectKey) o;

        return object != null ? object.equals(objectKey.object) : objectKey.object == null;
    }

    @Override
    public int hashCode() {
        return object != null ? object.hashCode() : 0;
    }
}
