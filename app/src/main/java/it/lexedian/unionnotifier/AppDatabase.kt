package it.lexedian.unionnotifier

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by Daniele Pellone on 05/12/2017.
 */

@Database(entities = arrayOf(Announcement::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun announcementsDAO(): AnnouncementsDAO

    companion object : SingletonHolder<AppDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
                AppDatabase::class.java, "Ann.db")
                .build()
    })
}