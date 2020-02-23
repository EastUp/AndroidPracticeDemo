package com.east.architect_zenghui.architect16_designmode9_proxy.simple3;

import com.east.architect_zenghui.architect16_designmode9_proxy.retrofit2.http.FormUrlEncoded;
import com.east.architect_zenghui.architect16_designmode9_proxy.retrofit2.http.POST;
import com.east.architect_zenghui.architect16_designmode9_proxy.retrofit2.http.PartMap;

import java.util.Map;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description:
 * @author: East
 * @date: 2020-02-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface ServiceInterface {

    /**
     * 用户登录
     * @return
     */
    @POST
    @FormUrlEncoded
    String userLogin(@PartMap Map<String,Object> params);

    String userRegister();
}
