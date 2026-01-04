package com.abdelrahman.raafat.memento.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdelrahman.raafat.memento.data.local.dao.ReminderDao
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity

@Database(entities = [ReminderEntity::class], version = 3, exportSchema = false)
abstract class MementoDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var Instance: MementoDatabase? = null
        fun getDatabase(context: Context): MementoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = MementoDatabase::class.java,
                    name = "memento_database"
                ).fallbackToDestructiveMigration(true)
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}