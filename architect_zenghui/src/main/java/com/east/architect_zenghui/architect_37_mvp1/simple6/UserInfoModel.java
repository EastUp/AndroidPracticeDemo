package com.east.architect_zenghui.architect_37_mvp1.simple6;


import com.east.architect_zenghui.architect_37_mvp1.retrofit.RetrofitClient;
import com.east.architect_zenghui.architect_37_mvp1.retrofit.UserInfo;
import com.east.architect_zenghui.architect_37_mvp1.simple6.base.BaseModel;

import io.reactivex.Observable;

/**
 * Created by hcDarren on 2018/1/1.
 */

public class UserInfoModel extends BaseModel implements UserInfoContract.UserInfoModel{
    // Model 获取数据
    @Override
    public Observable<UserInfo> getUsers(String token){
        return RetrofitClient.getServiceApi()
                .queryUserInfo(token)
                // .subscribeOn().observeOn().subscribe()
                // Subscriber 封装一下
                // 第二个坑 , 坑我们 返回值都是一个泛型，转换返回值泛型
                .compose(RetrofitClient.<UserInfo>transformer());
    }
}
