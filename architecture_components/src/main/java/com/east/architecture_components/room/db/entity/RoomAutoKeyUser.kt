package com.east.architecture_components.room.db.entity

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.room.*
import java.util.*

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-08-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Entity(tableName = "user" ,indices = [Index(value = ["uid"])])
class RoomAutoKeyUser {

    @PrimaryKey(autoGenerate = true)
    var uid:Int ?= null

    @ColumnInfo( name = "first_name")
    var firstName : String? = null

    @ColumnInfo( name = "last_name")
    var lastName : String? = null

    var date :Date ?= null

    @Ignore
    var picture : Bitmap?=null

    constructor(firstName: String?, lastName: String?) {
        this.firstName = firstName
        this.lastName = lastName
    }

    override fun toString(): String {
        return "$uid---$firstName----$lastName"
    }
}