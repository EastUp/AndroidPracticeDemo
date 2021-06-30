package com.shu_da_shi.myapplication.glide;

import java.security.MessageDigest;

public interface Key {
    void updateDiskCacheKey(MessageDigest md);

    byte[] getKeyBytes();
}
