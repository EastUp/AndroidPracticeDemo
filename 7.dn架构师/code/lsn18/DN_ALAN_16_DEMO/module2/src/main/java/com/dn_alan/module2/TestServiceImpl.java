package com.dn_alan.module2;

import android.util.Log;

import com.dn_alan.base.TestService;
import com.dn_alan.router_annotation.Route;

@Route(path = "/module2/service")
public class TestServiceImpl implements TestService {
    @Override
    public void test() {
        Log.i("Service", "我是Module2模块测试服务通信");
    }
}

