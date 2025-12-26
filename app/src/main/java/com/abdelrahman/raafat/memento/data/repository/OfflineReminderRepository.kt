package com.abdelrahman.raafat.memento.data.repository

import com.abdelrahman.raafat.memento.data.local.dao.ReminderDao
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.domain.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineReminderRepository @Inject constructor(
    private val reminderDao: ReminderDao
) : ReminderRepository {
    override suspend fun insertReminder(reminder: ReminderEntity): Boolean {
        val insertResult = reminderDao.insertReminder(reminder)
        return insertResult > 0
    }

    override suspend fun updateReminder(reminder: ReminderEntity): Boolean {
        val updateReminderResult = reminderDao.updateReminder(reminder)
        return updateReminderResult > 0
    }

    override suspend fun deleteReminder(reminder: ReminderEntity): Int {
        return reminderDao.deleteReminder(reminder)
    }

    override suspend fun softDeleteReminder(reminder: ReminderEntity): Boolean {
        val updatedReminder = reminder.copy(
            isDeleted = true,
            deletedAt = System.currentTimeMillis()
        )
        return updateReminder(updatedReminder)
    }

    override fun getReminderById(reminderId: Long): Flow<ReminderEntity> {
        return reminderDao.getReminderById(reminderId)
    }

    override fun getReminderByTitle(title: String): Flow<ReminderEntity> {
        return reminderDao.getReminderByTitle(title)
    }

    override fun getAllReminders(): Flow<List<ReminderEntity>> =
        reminderDao.getAllReminders()

    override fun getDashboardReminders(): Flow<List<ReminderEntity>> =
        reminderDao.getDashboardReminders()

    override fun getAllDoneReminders(): Flow<List<ReminderEntity>> =
        reminderDao.getAllDoneReminders()

}