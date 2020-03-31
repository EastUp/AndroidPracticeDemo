package com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple1;


import com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple2.BaseResult;

/**
 * Created by hcDarren on 2017/12/16.
 */

public class UserLoginResult extends BaseResult {
    // 成功是一个对象正常，不成功是一个 String （出错）
    public Object data;
}
