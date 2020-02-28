package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple2_responsiblity.handler

import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple2_responsiblity.UserInfo

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 检验用户的处理接口
 *  @author: East
 *  @date: 2020/2/27
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface IUserSystem {
    /**
     * 根据用户名和密码查询用户信息
     */
    fun queryUserInfo(userName:String,userPwd:String):UserInfo?
}