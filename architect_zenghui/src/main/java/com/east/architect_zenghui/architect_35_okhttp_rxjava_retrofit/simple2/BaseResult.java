package com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple2;

/**
 * Created by hcDarren on 2017/12/16.
 */

public class BaseResult {
    String bol;
    String msg;

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return bol;
    }

    public boolean isOk(){
        return "true".equals(bol);
    }
}
