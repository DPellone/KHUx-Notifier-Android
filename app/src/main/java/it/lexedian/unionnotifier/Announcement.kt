package it.lexedian.unionnotifier

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Daniele Pellone on 05/12/2017.
 */
@Entity
class Announcement(
        @PrimaryKey @ColumnInfo var id: Int = 0,
        @ColumnInfo var cat: String = "",
        @ColumnInfo var title: String = "") {

    var BASE_URL = "http://api.sp.kingdomhearts.com/"

    var isNew: Boolean = false

    var url: String = ""
        get() = BASE_URL + "information/detail/$id"

    override fun toString(): String {
        return cat + ": " + title
    }

    override fun equals(other: Any?): Boolean {
        if (other!!::class.java != Announcement::class.java)
            return false
        val otherA = other as Announcement
        if(otherA.id == this.id)
            return true
        return false
    }
}