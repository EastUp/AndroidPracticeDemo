package com.east.architect_zenghui.architect_37_mvp1.simple7.proxy;

/**
 * Created by hcDarren on 2018/1/6.
 */

public interface IMvpProxy {
    void bindAndCreatePresenter();// 一个是和创建绑定
    void unbindPresenter();// 一个是解绑
}
