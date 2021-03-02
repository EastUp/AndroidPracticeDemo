package com.shu_da_shi.myapplication.glide;

import java.io.File;
import java.io.Writer;

public interface DiskCache {

    interface Writer{
        boolean write(File file);
    }

    File get(Key key);

    void put(Key key, Writer writer);

    void delete(Key key);

    void clear();
}
