package it.lexedian.unionnotifier

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * Created by Daniele Pellone on 05/12/2017.
 */

@Dao
interface AnnouncementsDAO {
    @Query("SELECT * FROM announcement")
    fun getAll(): List<Announcement>

    @Query("SELECT * FROM announcement WHERE id=:arg0")
    fun get(Id: Int): List<Announcement>

    @Insert
    fun insertAll(ann: Announcement)

    @Delete
    fun delete(ann: Announcement)
}