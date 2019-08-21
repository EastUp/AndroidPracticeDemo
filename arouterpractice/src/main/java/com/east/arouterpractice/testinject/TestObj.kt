package com.east.arouterpractice.testinject

/**
 * TODO:Feature
 *
 * @author zhilong [Contact me.](mailto:zhilong.lzl@alibaba-inc.com)
 * @version 1.0
 * @since 2017/3/16 下午4:42
 */
class TestObj {
    var name: String ?= null
    var id: Int = 0

    constructor() {}

    constructor(name: String, id: Int) {
        this.name = name
        this.id = id
    }
}
