package com.east.architecture_components.room.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.east.architecture_components.room.db.onetomany.Book

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:   关联查询
 *  @author: East
 *  @date: 2019-08-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
class UserAndBook {
    @Embedded
    var user : RoomAutoKeyUser?= null

    @Relation(parentColumn = "uid",entityColumn = "user_id")
    var books:List<Book> ?= null
}