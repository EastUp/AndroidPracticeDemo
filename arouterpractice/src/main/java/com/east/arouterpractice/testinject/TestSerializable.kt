package com.east.arouterpractice.testinject

import java.io.Serializable

/**
 * Created by @author joker on 2018/7/10.
 */
class TestSerializable : Serializable {
    var name: String?= null
    var id: Int = 0

    constructor() {}

    constructor(name: String, id: Int) {
        this.name = name
        this.id = id
    }
}
