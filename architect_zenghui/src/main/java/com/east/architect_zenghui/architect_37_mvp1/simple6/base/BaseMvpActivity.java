package com.east.architect_zenghui.architect_37_mvp1.simple6.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.east.architect_zenghui.architect_37_mvp1.simple6.inject.InjectPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcDarren on 2018/1/1.
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    private P mPresenter;
    private List<BasePresenter> mPresenters;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        mPresenters = new ArrayList<>();
        // 创建 P，创建只能交给 子类，每个 Activity 都不一样
        mPresenter = createPresenter();
        mPresenter.attach(this);

        // 1. Activity ？ Fragment? 1,2  ViewGroup ? 抽离  工具类抽出去，或者把代码每个地方 copy 一份
        // 今天你可以抽离目前的一部分：注入代码 + 额外功能 + 还有一些其他地方又不一样

        // 这个地方要去注入 Presenter 通过反射 (Dagger) soEasy
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if(injectPresenter != null){
                // 创建注入
                Class<? extends BasePresenter> presenterClazz = null;
                // 自己去判断一下类型？ 获取继承的父类，如果不是 继承 BasePresenter 抛异常
                try {
                    presenterClazz = (Class<? extends BasePresenter>) field.getType();
//                    presenterClazz.asSubclass(BasePresenter.class);//判断是否是BasePresenter的子类，如果不是就会抛出异常
                    boolean from = BasePresenter.class.isAssignableFrom(presenterClazz);//A.class.isAssignableFrom(B.class) 判断A是否是B的父接口或超类或A跟B是同一个类或接口
                    if(!from){
                        throw new RuntimeException("No support inject presenter type " + field.getType().getName());
                    }
                } catch (Exception e){
                    // 乱七八糟一些注入
                    throw new RuntimeException("No support inject presenter type " + field.getType().getName());
                }

                try {
                    // 创建 Presenter 对象
                    BasePresenter basePresenter = presenterClazz.newInstance();
                    // 并没有解绑，还是会有问题，这个怎么办？1 2
                    basePresenter.attach(this);
                    // 设置
                    field.setAccessible(true);
                    field.set(this,basePresenter);
                    mPresenters.add(basePresenter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        initView();
        initData();
    }

    // 由子类去实现创建
    protected abstract P createPresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void setContentView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 一定要解绑
        for (BasePresenter presenter : mPresenters) {
            presenter.detach();
        }
        mPresenter.detach();
    }

    public P getPresenter() {
        return mPresenter;
    }
}
