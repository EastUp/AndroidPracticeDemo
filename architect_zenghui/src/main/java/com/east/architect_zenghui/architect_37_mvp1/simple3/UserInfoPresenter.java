package com.east.architect_zenghui.architect_37_mvp1.simple3;


import com.east.architect_zenghui.architect_37_mvp1.retrofit.BaseSubscriber;
import com.east.architect_zenghui.architect_37_mvp1.retrofit.UserInfo;

/**
 * Created by hcDarren on 2018/1/1.
 */
public class UserInfoPresenter implements UserInfoContract.UserInfoPresenter{
    // 肯定会持有 V 和 M
    private UserInfoContract.UserInfoView mView;
    private UserInfoContract.UserInfoModel mModel;

    public UserInfoPresenter(){
        mModel = new UserInfoModel();
    }

    // 解绑 View 加了这个之后越来越复杂，代码写起来越来越多？怎么办？
    // 问题是，1. 很多代码是公用反复的，attach detach 每个 Presenter 都要有
    //         2. Activity -> View 的 attach detach 每个 View 层也要有
    public void attach(UserInfoContract.UserInfoView view){
        this.mView = view;
    }
    public void detach(){
        this.mView = null;
    }

    @Override
    public void getUsers(String token) {
        // RxJava  + OkHttp + Retrofit
        // 网络引擎 + OkHttp
        // 显示正在加载中
        mView.onLoading();
        // 耗时 3s
        mModel.getUsers(token).subscribe(new BaseSubscriber<UserInfo>() {
            @Override
            protected void onError(String errorCode, String errorMessage) {
                // 失败
                if(mView != null)
                mView.onError();
            }

            @Override
            public void onNext(UserInfo userInfo) {
                // 成功
                if(mView != null)
                mView.onSucceed(userInfo);
            }
        });
    }
}
