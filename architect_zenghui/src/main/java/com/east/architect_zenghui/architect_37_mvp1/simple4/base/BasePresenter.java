package com.east.architect_zenghui.architect_37_mvp1.simple4.base;

/**
 * Created by hcDarren on 2018/1/1.
 */

public class BasePresenter<V extends BaseView> {
    // 目前两个两个公用方法 ，传递的时候 会有不同的 View ，怎么办？泛型
    private V mView;
    // View 有一个特点，都是接口
    // GC 回收的算法机制（哪几种）标记清楚法

    public void attach(V view){
        this.mView = view;
    }
    public void detach(){
        // 不解绑的问题 Activity -> Presenter  ,Presenter 持有了 Activity 应该是会有内存泄漏
        this.mView = null;
    }

    public V getView() {
        return mView;
    }
}
