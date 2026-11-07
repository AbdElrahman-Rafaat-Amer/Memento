package com.abdelrahman.raafat.memento.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdelrahman.raafat.memento.data.local.converter.RecurrenceConverter
import com.abdelrahman.raafat.memento.data.local.dao.ReminderDao
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity

@TypeConverters(RecurrenceConverter::class)
@Database(entities = [ReminderEntity::class], version = 6, exportSchema = false)
abstract class MementoDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}
