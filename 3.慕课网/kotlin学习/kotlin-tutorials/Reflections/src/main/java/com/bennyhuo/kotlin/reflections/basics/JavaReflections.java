package com.bennyhuo.kotlin.reflections.basics;

import java.lang.reflect.Field;

public class JavaReflections {
    public static void main(String... args) throws NoSuchFieldException {
        Class<String> cls = String.class;

        Class<? extends Object> clsOfObj = String.class;

        Field field = cls.getDeclaredField("value");

    }
}
