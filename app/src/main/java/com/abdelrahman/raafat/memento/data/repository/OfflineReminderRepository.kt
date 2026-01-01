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
        //TODO Rollback the insert operation if the reminder is not scheduled
        try {
            val newId = reminderDao.insertReminder(reminder)
            val isSuccessfulInsert = newId > 0

            return if (isSuccessfulInsert) {
                trySchedulingReminder(reminder.copy(id = newId))
            } else {
                ReminderScheduleResult.DataBaseError("Failed to insert reminder")
            }
        } catch (exception: Exception) {
            return ReminderScheduleResult.DataBaseError("Failed to insert reminder ${exception.message}")
        }
    }

    override suspend fun updateReminder(reminder: ReminderEntity): ReminderScheduleResult {
        return updateReminder(
            reminder = reminder,
            onSuccessAction = {
                scheduler.cancelReminder(reminderId = reminder.id)
                trySchedulingReminder(reminder.copy(id = reminder.id))
            }
        )
    }

    override suspend fun markReminderAsDone(reminder: ReminderEntity): ReminderScheduleResult {
        return updateReminder(
            reminder = reminder,
            onSuccessAction = {
                scheduler.cancelReminder(reminderId = reminder.id)
                ReminderScheduleResult.Success
            }
        )
    }

    override suspend fun markReminderAsNotDone(reminder: ReminderEntity): ReminderScheduleResult {
        return updateReminder(
            reminder = reminder,
            onSuccessAction = {
                trySchedulingReminder(reminder.copy(id = reminder.id))
            }
        )
    }

    override suspend fun deleteReminder(reminder: ReminderEntity): ReminderScheduleResult {
        val isSuccessfulDelete = reminderDao.deleteReminder(reminder) > 0
        return if (isSuccessfulDelete) {
            scheduler.cancelReminder(reminderId = reminder.id)
            ReminderScheduleResult.Success
        } else {
            ReminderScheduleResult.DataBaseError("Failed to delete reminder $reminder")
        }
    }

    override suspend fun softDeleteReminder(reminder: ReminderEntity): ReminderScheduleResult {
        val updatedReminder = reminder.copy(
            isDeleted = true,
            deletedAt = System.currentTimeMillis()
        )
        return updateReminder(
            reminder = updatedReminder,
            onSuccessAction = {
                scheduler.cancelReminder(reminderId = updatedReminder.id)
                ReminderScheduleResult.Success
            }
        )
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

    /**
     * A helper function to perform a database update and execute a follow-up action on success.
     * @param reminder The entity to update in the database.
     * @param onSuccessAction The action to perform if the database update is successful.
     * @return The result of the operation.
     */
    private suspend fun updateReminder(
        reminder: ReminderEntity,
        onSuccessAction: (ReminderEntity) -> ReminderScheduleResult
    ): ReminderScheduleResult {
        try {
            val rowsAffected = reminderDao.updateReminder(reminder)
            return if (rowsAffected > 0) {
                onSuccessAction(reminder)
            } else {
                ReminderScheduleResult.DataBaseError("Failed to update reminder: $reminder")
            }
        } catch (exception: Exception) {
            return ReminderScheduleResult.DataBaseError("Failed to update reminder: ${exception.message}")
        }
    }

    /**
     * Schedules a reminder and wraps the result, handling potential exceptions.
     */
    private fun trySchedulingReminder(reminder: ReminderEntity): ReminderScheduleResult {
        return try {
            scheduler.scheduleReminder(
                reminderId = reminder.id,
                triggerAtMillis = reminder.toTriggerMillis(),
                title = reminder.title,
                additionalInfo = reminder.additionalInfo
            )
            ReminderScheduleResult.Success
        } catch (exception: PastTriggerException) {
            ReminderScheduleResult.PastTrigger(exception.message)
        } catch (exception: ExactAlarmPermissionException) {
            ReminderScheduleResult.ExactAlarmPermissionMissing(exception.message)
        } catch (exception: SecurityException) {
            ReminderScheduleResult.UnknownError("A security error occurred: ${exception.message}")
        } catch (exception: Exception) {
            ReminderScheduleResult.UnknownError(exception.message)
        }
    }
}