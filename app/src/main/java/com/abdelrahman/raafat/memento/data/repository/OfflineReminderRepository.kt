package com.abdelrahman.raafat.memento.data.repository

import com.abdelrahman.raafat.memento.data.local.dao.ReminderDao
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.data.local.entity.toTriggerMillis
import com.abdelrahman.raafat.memento.domain.repository.ReminderRepository
import com.abdelrahman.raafat.memento.domain.result.ReminderScheduleResult
import com.abdelrahman.raafat.memento.domain.exceptions.ExactAlarmPermissionException
import com.abdelrahman.raafat.memento.domain.exceptions.PastTriggerException
import com.abdelrahman.raafat.memento.domain.reminder.ReminderNotificationScheduler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineReminderRepository @Inject constructor(
    private val reminderDao: ReminderDao,
    private val scheduler: ReminderNotificationScheduler
) : ReminderRepository {
    override suspend fun insertReminder(reminder: ReminderEntity): ReminderScheduleResult {
        val insertResult = reminderDao.insertReminder(reminder)

        if (insertResult <= 0) {
            return ReminderScheduleResult.DataBaseError("Failed to insert reminder")
        }

        return try {
            scheduler.scheduleReminder(
                reminderId = insertResult,
                triggerAtMillis = reminder.toTriggerMillis(),
                title = reminder.title,
                additionalInfo = reminder.additionalInfo
            )
            ReminderScheduleResult.Success
        } catch (exception: PastTriggerException) {
            ReminderScheduleResult.PastTrigger(exception.message)
        } catch (exception: ExactAlarmPermissionException) {
            ReminderScheduleResult.ExactAlarmPermissionMissing(exception.message)
        } catch (exception: Exception) {
            ReminderScheduleResult.UnknownError(exception.message)
        }
    }

    override suspend fun updateReminder(reminder: ReminderEntity): Boolean {
        val updateReminderResult = reminderDao.updateReminder(reminder)
        return updateReminderResult > 0
    }

    override suspend fun markReminderAsDone(reminder: ReminderEntity): Boolean {
        val updateReminderResult = reminderDao.updateReminder(reminder)
        return if (updateReminderResult > 0) {
            scheduler.cancelReminder(reminderId = reminder.id)
            true
        } else {
            false
        }
    }

    override suspend fun unDoMarkReminderAsDone(reminder: ReminderEntity): Boolean {
        val updateReminderResult = reminderDao.updateReminder(reminder)
        return if (updateReminderResult > 0) {
            try {
                scheduler.scheduleReminder(
                    reminderId = reminder.id,
                    triggerAtMillis = reminder.toTriggerMillis(),
                    title = reminder.title,
                    additionalInfo = reminder.additionalInfo
                )
                true
            } catch (_: PastTriggerException) {
                false
            } catch (_: ExactAlarmPermissionException) {
                false
            } catch (_: Exception) {
                false
            }
        } else {
            false
        }
    }

    override suspend fun deleteReminder(reminder: ReminderEntity): Int {
        val deleteReminderResult = reminderDao.deleteReminder(reminder)
        if (deleteReminderResult > 0) {
            scheduler.cancelReminder(reminderId = reminder.id)
        }
        return deleteReminderResult
    }

    override suspend fun softDeleteReminder(reminder: ReminderEntity): Boolean {
        val updatedReminder = reminder.copy(
            isDeleted = true,
            deletedAt = System.currentTimeMillis()
        )
        val updatedReminderResult = reminderDao.updateReminder(updatedReminder)

        return if (updatedReminderResult > 0) {
            scheduler.cancelReminder(reminderId = reminder.id)
            true
        } else {
            false
        }
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