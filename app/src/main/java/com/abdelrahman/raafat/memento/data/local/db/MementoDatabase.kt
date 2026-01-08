package com.abdelrahman.raafat.memento.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abdelrahman.raafat.memento.data.local.dao.ReminderDao
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity

@Database(entities = [ReminderEntity::class], version = 4, exportSchema = false)
abstract class MementoDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}
