package com.east.architecture_components.room.db.onetomany

import androidx.room.*
import com.east.architecture_components.room.db.entity.RoomAutoKeyUser

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 外键依赖RoomAutoKeyUser表,RoomAutoUser是父表,Book是字表
 *                一对多的关系 (查询的时候使用关联查询 详情请看 UserAndBook类和)
 *  @author: East
 *  @date: 2019-08-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Entity(tableName = "book",indices = arrayOf(Index(value = ["user_id"])),foreignKeys = [ForeignKey(entity = RoomAutoKeyUser::class,
    parentColumns = arrayOf("uid"),
    childColumns = arrayOf("user_id")/*,
                                                        onUpdate = ForeignKey.CASCADE, //父表更新时,子表跟着更新
                                                        onDelete = ForeignKey.RESTRICT*/)]
)//当parent中的key有依赖的时候禁止对parent做动作，做动作就会报错。

//onDelete：默认NO_ACTION，当parent里面有删除操作的时候，child表可以做的Action动作有：
//       1. NO_ACTION：当parent中的key有变化的时候child不做任何动作。
//       2. RESTRICT：当parent中的key有依赖的时候禁止对parent做动作，做动作就会报错。
//       3. SET_NULL：当paren中的key有变化的时候child中依赖的key会设置为NULL。
//       4. SET_DEFAULT：当parent中的key有变化的时候child中依赖的key会设置为默认值。
//       5. CASCADE：当parent中的key有变化的时候child中依赖的key会跟着变化。
//onUpdate：默认NO_ACTION，当parent里面有更新操作的时候，child表需要做的动作。Action动作方式和onDelete是一样的。
//deferred：默认值false，在事务完成之前，是否应该推迟外键约束。这个怎么理解，当我们启动一个事务插入很多数据的时候，事务还没完成之前。当parent引起key变化的时候。可以设置deferred为ture。让key立即改变。


/**
 *  一对多的关系:注意添加book的时候 book一定属于某个RoomAutoKeyUser 即userId一定属于某个RoomAutoKeyUser
 *  否则会报错 : FOREIGN KEY constraint failed (code 787)
 */
class Book {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    var bookId :Int = 0

    @ColumnInfo(name = "user_id")
    var userId : Int = 0

    var title : String ?= null

    constructor(userId: Int, title: String?) {
        this.userId = userId
        this.title = title
    }

    override fun toString(): String {
        return "$bookId---$userId----$title"
    }


}