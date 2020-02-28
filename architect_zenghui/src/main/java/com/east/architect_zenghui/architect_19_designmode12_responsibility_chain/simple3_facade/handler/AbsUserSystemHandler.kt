package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.handler

import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple3_facade.UserInfo


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 责任链模式-抽象处理者角色
 *  @author: East
 *  @date: 2020/2/27
 * |---------------------------------------------------------------------------------------------------------------|
 */
abstract class AbsUserSystemHandler : IUserSystemHandler<AbsUserSystemHandler>, IUserSystem {

    protected var mAbsUserSystemHandler: AbsUserSystemHandler? = null

    override fun nextHandler(systemHandler: AbsUserSystemHandler) {
        this.mAbsUserSystemHandler = systemHandler
    }

    override fun queryUserInfo(userName: String, userPwd: String): UserInfo? {
        return queryUserInfoInOwnCollections(userName, userPwd)
            ?: if (mAbsUserSystemHandler != null)
                mAbsUserSystemHandler!!.queryUserInfo(userName, userPwd)
            else
                null
    }

    /**
     * 在自己的集合中查询适合有用户
     */
    abstract fun queryUserInfoInOwnCollections(userName: String, userPwd: String): UserInfo?

}