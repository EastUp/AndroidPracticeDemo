package com.east.architect_zenghui.architect_37_mvp1.simple6;


import com.east.architect_zenghui.architect_37_mvp1.retrofit.BaseSubscriber;
import com.east.architect_zenghui.architect_37_mvp1.retrofit.UserInfo;
import com.east.architect_zenghui.architect_37_mvp1.simple6.base.BasePresenter;

/**
 * Created by hcDarren on 2018/1/1.
 */
// 写一堆文档，一些事例，做一些讲解
public class UserInfoPresenter extends BasePresenter<UserInfoContract.UserInfoView, UserInfoModel> implements UserInfoContract.UserInfoPresenter {
    // 是直接 new 还是？一个 Presneter 对应多个 Model 怎么解决？ new 很正常 ，尽量分离（六大基本原则）
    // 一般情况下是 1 个 Presneter 对应一个 Model
    // 如果说你的项目有这种一对多的情况（待会参考我写的 1 个 View 对应多个 Presneter 的解决方案）
    // 写一个 一对一的情况

    // 解绑 View 加了这个之后越来越复杂，代码写起来越来越多？怎么办？
    // 问题是，1. 很多代码是公用反复的，attach detach 每个 Presenter 都要有
    //         2. Activity -> View 的 attach detach 每个 View 层也要有
    @Override
    public void getUsers(String token) {
        // RxJava  + OkHttp + Retrofit
        // 网络引擎 + OkHttp
        // 显示正在加载中
        getView().onLoading();
        // 耗时 3s
        getModel().getUsers(token).subscribe(new BaseSubscriber<UserInfo>() {
            @Override
            protected void onError(String errorCode, String errorMessage) {
                // 失败
                // 每次都需要去判断 View != null ,这个也很麻烦，怎么办怎么办？
                // 都是接口 ，通用代码 View != null 统一处理，这个是 AOP (aspectJ,动态代理)
                getView().onError();
            }

            @Override
            public void onNext(UserInfo userInfo) {
                // 成功
                getView().onSucceed(userInfo);
            }
        });
    }
}
