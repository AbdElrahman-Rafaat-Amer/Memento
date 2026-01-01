package com.abdelrahman.raafat.memento.domain.repository

import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.domain.result.ReminderScheduleResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve
 * of [com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity]
 * from a given data source.
 */
interface ReminderRepository {
    suspend fun insertReminder(reminder: ReminderEntity): ReminderScheduleResult

    suspend fun updateReminder(reminder: ReminderEntity): ReminderScheduleResult

    suspend fun markReminderAsDone(reminder: ReminderEntity): ReminderScheduleResult

    suspend fun markReminderAsNotDone(reminder: ReminderEntity): ReminderScheduleResult

    suspend fun deleteReminder(reminder: ReminderEntity): ReminderScheduleResult

    suspend fun softDeleteReminder(reminder: ReminderEntity): ReminderScheduleResult

    fun getReminderById(reminderId: Long): Flow<ReminderEntity>

    fun getReminderByTitle(title: String): Flow<ReminderEntity>

    fun getAllReminders(): Flow<List<ReminderEntity>>
    fun getDashboardReminders(): Flow<List<ReminderEntity>>

    fun getAllDoneReminders(): Flow<List<ReminderEntity>>

}