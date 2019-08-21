package com.east.arouterpractice.testinject

import android.os.Parcel
import android.os.Parcelable

/**
 * TODO:Feature
 *
 * @author zhilong [Contact me.](mailto:zhilong.lzl@alibaba-inc.com)
 * @version 1.0
 * @since 2017/3/16 下午4:42
 */
class TestParcelable : Parcelable {
    var name: String? = null

    var id: Int = 0

    constructor() {}

    constructor(name: String, id: Int) {
        this.name = name
        this.id = id
    }

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TestParcelable> = object : Parcelable.Creator<TestParcelable> {
            override fun createFromParcel(source: Parcel): TestParcelable = TestParcelable(source)
            override fun newArray(size: Int): Array<TestParcelable?> = arrayOfNulls(size)
        }
    }
}
