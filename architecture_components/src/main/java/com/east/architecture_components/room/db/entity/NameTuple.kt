package com.east.architecture_components.room.db.entity

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  有时可能只需要Entity的几个field，例如只需要获取User的姓名就行了。通过只获取这两列的数据不仅能够节省宝贵的资源，还能加快查询速度。
 *  @author: East
 *  @date: 2019-08-08
 * |---------------------------------------------------------------------------------------------------------------|
 */
data class NameTuple(var first_name: String, var last_name: String){
    override fun toString(): String {
        return "$first_name==$last_name"
    }
}