package com.shu_da_shi.myapplication.glide;

import java.io.File;

interface ModelTypes<T> {
    T load(String string);

    T load(File file);

}
