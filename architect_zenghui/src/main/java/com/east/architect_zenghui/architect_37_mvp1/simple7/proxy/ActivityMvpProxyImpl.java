package com.east.architect_zenghui.architect_37_mvp1.simple7.proxy;


import com.east.architect_zenghui.architect_37_mvp1.simple7.base.BaseView;

/**
 * Created by hcDarren on 2018/1/6.
 */

public class ActivityMvpProxyImpl<V extends BaseView> extends MvpProxyImpl<V> implements ActivityMvpProxy{
    public ActivityMvpProxyImpl(V view) {
        super(view);
    }
    // 不同对待，一般可能不会


}
