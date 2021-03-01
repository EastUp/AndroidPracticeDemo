package com.shu_da_shi.myapplication.glide.cache;


import java.security.MessageDigest;

public interface Key {

    void updateDiskCacheKey(MessageDigest messageDigest);

    byte[] getKeyBytes();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
