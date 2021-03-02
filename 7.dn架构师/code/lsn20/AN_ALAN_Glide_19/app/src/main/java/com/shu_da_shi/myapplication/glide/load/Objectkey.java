package com.shu_da_shi.myapplication.glide.load;

import com.shu_da_shi.myapplication.glide.Key;

import java.security.MessageDigest;
import java.util.Objects;

public class Objectkey implements Key {

    private final Object object;  //http://wwww.xxxxxx.xxxxx

    public Objectkey(Object object) {
        this.object = object;
    }

    /**
     * MessageDigest 可以用此类可以对数据加密。加密的方式可以是MD5 或 SHA
     * @param md
     */
    @Override
    public void updateDiskCacheKey(MessageDigest md) {
        md.update(getKeyBytes());
    }

    @Override
    public byte[] getKeyBytes() {
        return object.toString().getBytes();
    }

    /**
     * ArrayList和hasmap
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objectkey objectkey = (Objectkey) o;
        return object != null ? object.equals(objectkey.object) : objectkey.object == null;
    }

    @Override
    public int hashCode() {
        return object != null ? object.hashCode() : 0;
    }
}
