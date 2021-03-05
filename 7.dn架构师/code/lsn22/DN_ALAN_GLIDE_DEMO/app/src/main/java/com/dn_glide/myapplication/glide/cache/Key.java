package com.dn_glide.myapplication.glide.cache;

import java.security.MessageDigest;

public interface Key {

    void updateDiskCacheKey(MessageDigest md);

    byte[] getKeyBytes();
}
