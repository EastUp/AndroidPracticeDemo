package com.east.architect_zenghui.architect_37_mvp1.simple5.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hcDarren on 2018/1/1.
 */

public class BasePresenter<V extends BaseView> {
    // 目前两个两个公用方法 ，传递的时候 会有不同的 View ，怎么办？泛型
    private WeakReference<V> mViewReference;
    private V mProxyView;
    // View 一般都是 Activity ,涉及到内存泄漏，但是已经解绑了不会，如果没解绑就会泄漏
    // 最好还是用一下软引用

    // View 有一个特点，都是接口
    // GC 回收的算法机制（哪几种）标记清楚法
    public void attach(V view){
        this.mViewReference = new WeakReference<V>(view);

        // 用代理对象
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 执行这个方法 ，调用的是被代理的对象
                if(mViewReference == null||mViewReference.get() == null) {
                    return null;
                }
                // 没解绑执行的是原始被代理 View 的方法
                return method.invoke(mViewReference.get(),args);
            }
        });
    }
    public void detach(){
        // 不解绑的问题 Activity -> Presenter  ,Presenter 持有了 Activity 应该是会有内存泄漏
        this.mViewReference.clear();
        this.mViewReference = null;
        this.mProxyView = null;
    }

    public V getView() {
        return mProxyView;
    }
}
