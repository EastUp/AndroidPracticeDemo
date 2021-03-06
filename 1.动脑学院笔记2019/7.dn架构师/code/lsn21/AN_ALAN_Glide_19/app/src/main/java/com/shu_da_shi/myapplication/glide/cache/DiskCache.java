package com.shu_da_shi.myapplication.glide.cache;

import java.io.File;

public interface DiskCache {

    interface Writer{
        boolean write(File file);
    }

    File get(Key key);

    void put(Key key, Writer writer);

    void delete(Key key);

    void clear();
}
