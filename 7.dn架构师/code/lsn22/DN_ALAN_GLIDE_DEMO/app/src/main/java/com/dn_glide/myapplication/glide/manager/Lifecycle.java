package com.dn_glide.myapplication.glide.manager;

public interface Lifecycle {

    void addListener(LifecycleListener listener);

    void removeListener(LifecycleListener listener);
}
