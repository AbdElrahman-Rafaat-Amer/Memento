package com.abdelrahman.raafat.memento.data.repository

import com.abdelrahman.raafat.memento.data.local.dao.ReminderDao
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

class OfflineReminderRepository (
    private val reminderDao: ReminderDao
) : ReminderRepository {
    override suspend fun insertReminder(reminder: ReminderEntity): Int {
       return reminderDao.insertReminder(reminder)
    }

    override suspend fun updateReminder(reminder: ReminderEntity): Int {
        return reminderDao.updateReminder(reminder)
    }

    override suspend fun deleteReminder(reminderId: Long): Int {
        return reminderDao.deleteReminder(reminderId)
    }

    override fun getReminderById(reminderId: Long): Flow<ReminderEntity> {
        return reminderDao.getReminderById(reminderId)
    }

    override fun getReminderByTitle(title: String): Flow<ReminderEntity> {
        return reminderDao.getReminderByTitle(title)
    }

    override fun getAllReminders(): Flow<List<ReminderEntity>> {
        return reminderDao.getAllReminders()
    }

    override fun getAllDoneReminders(): Flow<List<ReminderEntity>> {
        return reminderDao.getAllDoneReminders()
    }

}