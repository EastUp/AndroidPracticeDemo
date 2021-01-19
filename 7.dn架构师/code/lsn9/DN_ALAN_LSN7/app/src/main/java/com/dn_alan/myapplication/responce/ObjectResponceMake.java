package com.dn_alan.myapplication.responce;

import com.dn_alan.myapplication.bean.RequestBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  执行单例的方法
 *  @author: jamin
 *  @date: 2021/1/19 11:07
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class ObjectResponceMake extends ResponceMake {
    private Method mMethod;

    private Object mObject;

    @Override
    protected Object invokeMethod() {

        Exception exception;
        try {
            return mMethod.invoke(mObject,mParameters); // 执行方法
        } catch (IllegalAccessException e) {
            exception = e;
        } catch (InvocationTargetException e) {
            exception = e;
        }
        return null;
    }
//  1
    @Override
    protected void setMethod(RequestBean requestBean) {
        // 获取之前单例存储的对象
        mObject = OBJECT_CENTER.getObject(reslutClass.getName());
//        getUser()    ---->method
        Method method = typeCenter.getMethod(mObject.getClass(), requestBean);
        mMethod = method;
    }
}
