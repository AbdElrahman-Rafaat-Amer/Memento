package com.abdelrahman.raafat.memento.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert
    suspend fun insertReminder(reminder: ReminderEntity) : Long

    @Update
    suspend fun updateReminder(reminder: ReminderEntity) : Int

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity) : Int

    @Query("SELECT * FROM ReminderEntity Where id = :reminderId")
    fun getReminderById(reminderId: Long) : Flow<ReminderEntity>

    @Query("SELECT * FROM ReminderEntity Where title = :title")
    fun getReminderByTitle(title: String) : Flow<ReminderEntity>

    @Query("SELECT * FROM ReminderEntity ORDER BY id ASC")
    fun getAllReminders() : Flow<List<ReminderEntity>>

    @Query("SELECT * FROM ReminderEntity WHERE isDone = 0 AND isDeleted = 0 ORDER BY id ASC")
    fun getDashboardReminders(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM ReminderEntity Where isDone = 1 ORDER BY id ASC")
    fun getAllDoneReminders() : Flow<List<ReminderEntity>>
}