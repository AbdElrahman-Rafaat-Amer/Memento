package com.abdelrahman.raafat.memento.domain.repository

import com.abdelrahman.raafat.memento.domain.model.Reminder
import com.abdelrahman.raafat.memento.domain.result.ReminderScheduleResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve
 * of [com.abdelrahman.raafat.memento.domain.model.Reminder]
 * from a given data source.
 */
interface ReminderRepository {
    suspend fun insertReminder(reminder: Reminder): ReminderScheduleResult

    suspend fun updateReminder(reminder: Reminder): ReminderScheduleResult

    suspend fun markReminderAsDone(reminder: Reminder): ReminderScheduleResult

    suspend fun markReminderAsNotDone(reminder: Reminder): ReminderScheduleResult

    suspend fun deleteReminder(reminder: Reminder): ReminderScheduleResult

    suspend fun softDeleteReminder(reminder: Reminder): ReminderScheduleResult

    fun getReminderById(reminderId: Long): Flow<Reminder>

    fun getReminderByTitle(title: String): Flow<Reminder>

    fun getAllReminders(): Flow<List<Reminder>>

    fun getDashboardReminders(): Flow<List<Reminder>>

    fun getAllDoneReminders(): Flow<List<Reminder>>

    suspend fun markAsSnoozed(id: Long)
    suspend fun clearSnooze(id: Long)

}