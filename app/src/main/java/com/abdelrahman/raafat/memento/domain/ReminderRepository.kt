package com.abdelrahman.raafat.memento.domain

import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve
 * of [com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity]
 * from a given data source.
 */
interface ReminderRepository {
    suspend fun insertReminder(reminder: ReminderEntity) : Boolean

    suspend fun updateReminder(reminder: ReminderEntity) : Int

    suspend fun deleteReminder(reminder: ReminderEntity) : Int

    fun getReminderById(reminderId: Long) : Flow<ReminderEntity>

    fun getReminderByTitle(title: String) : Flow<ReminderEntity>

    fun getAllReminders() : Flow<List<ReminderEntity>>

    fun getAllDoneReminders() : Flow<List<ReminderEntity>>

}