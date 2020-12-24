package com.example.wuyang.lsn4code;

public class MyObjectPool extends ObjectPool{
    public MyObjectPool(int initialCapacity, int maxCapacity) {
        super(initialCapacity, maxCapacity);
    }

    public MyObjectPool(int maxCapacity) {
        super(maxCapacity);
    }

    @Override
    protected Object create() {
        return new Object();
    }
}
