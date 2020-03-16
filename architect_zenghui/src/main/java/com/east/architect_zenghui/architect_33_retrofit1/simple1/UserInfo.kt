package com.east.architect_zenghui.architect_33_retrofit1.simple1

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/16
 * |---------------------------------------------------------------------------------------------------------------|
 */
data class UserInfo (var userName:String,var userSex:String){
    override fun toString(): String {
        return "UserInfo(userName='$userName', userSex='$userSex')"
    }
}