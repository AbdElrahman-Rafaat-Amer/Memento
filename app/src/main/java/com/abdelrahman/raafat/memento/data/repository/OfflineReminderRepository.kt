package com.abdelrahman.raafat.memento.data.repository

import android.util.Log
import com.abdelrahman.raafat.memento.data.local.dao.ReminderDao
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.data.mapper.ReminderEntityMapper
import com.abdelrahman.raafat.memento.domain.repository.ReminderRepository
import com.abdelrahman.raafat.memento.domain.result.ReminderScheduleResult
import com.abdelrahman.raafat.memento.domain.exceptions.ExactAlarmPermissionException
import com.abdelrahman.raafat.memento.domain.exceptions.PastTriggerException
import com.abdelrahman.raafat.memento.domain.model.Reminder
import com.abdelrahman.raafat.memento.domain.model.toTriggerMillis
import com.abdelrahman.raafat.memento.domain.reminder.ReminderNotificationScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineReminderRepository @Inject constructor(
    private val reminderDao: ReminderDao,
    private val scheduler: ReminderNotificationScheduler,
    private val entityMapper: ReminderEntityMapper,
) : ReminderRepository {
    override suspend fun insertReminder(reminder: Reminder): ReminderScheduleResult {
        //TODO Rollback the insert operation if the reminder is not scheduled
        try {
            val entity = entityMapper.toEntity(reminder)
            val newId = reminderDao.insertReminder(entity)
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

    override suspend fun updateReminder(reminder: Reminder): ReminderScheduleResult {
        val entity = entityMapper.toEntity(reminder)
        return updateReminder(
            reminder = entity,
            onSuccessAction = {
                scheduler.cancelReminder(reminderId = reminder.id)
                trySchedulingReminder(reminder.copy(id = reminder.id))
            }
        )
    }

    override suspend fun markReminderAsDone(reminder: Reminder): ReminderScheduleResult {
        val entity = entityMapper.toEntity(reminder)
        return updateReminder(
            reminder = entity,
            onSuccessAction = {
                scheduler.cancelReminder(reminderId = reminder.id)
                ReminderScheduleResult.Success
            }
        )
    }

    override suspend fun markReminderAsNotDone(reminder: Reminder): ReminderScheduleResult {
        val entity = entityMapper.toEntity(reminder)
        return updateReminder(
            reminder = entity,
            onSuccessAction = {
                trySchedulingReminder(reminder.copy(id = reminder.id))
            }
        )
    }

    override suspend fun deleteReminder(reminder: Reminder): ReminderScheduleResult {
        val entity = entityMapper.toEntity(reminder)
        val isSuccessfulDelete = reminderDao.deleteReminder(entity) > 0
        return if (isSuccessfulDelete) {
            scheduler.cancelReminder(reminderId = reminder.id)
            ReminderScheduleResult.Success
        } else {
            ReminderScheduleResult.DataBaseError("Failed to delete reminder $reminder")
        }
    }

    override suspend fun softDeleteReminder(reminder: Reminder): ReminderScheduleResult {
        val entity = entityMapper.toEntity(reminder)
        val updatedEntity = entity.copy(
            isDeleted = true,
            deletedAt = System.currentTimeMillis()
        )
        return updateReminder(
            reminder = updatedEntity,
            onSuccessAction = {
                scheduler.cancelReminder(reminderId = updatedEntity.id)
                ReminderScheduleResult.Success
            }
        )
    }

    override fun getReminderById(reminderId: Long): Flow<Reminder> {
        val entity = reminderDao.getReminderById(reminderId)
        return entity.map {
            entityMapper.toDomain(it)
        }
    }

    override fun getReminderByTitle(title: String): Flow<Reminder> {
        val entity = reminderDao.getReminderByTitle(title)
        return entity.map {
            entityMapper.toDomain(it)
        }
    }

    override fun getAllReminders(): Flow<List<Reminder>> {
        val entities = reminderDao.getAllReminders()
        return entities.map {
            entityMapper.toDomainList(it)
        }
    }

    override fun getDashboardReminders(): Flow<List<Reminder>> {
        val entities = reminderDao.getDashboardReminders()
        return entities.map {
            entityMapper.toDomainList(it)
        }
    }

    override fun getAllDoneReminders(): Flow<List<Reminder>> {
        val entities = reminderDao.getAllDoneReminders()
        return entities.map {
            entityMapper.toDomainList(it)
        }
    }

    override suspend fun markAsSnoozed(id: Long) {
        val markAsSnoozedState = reminderDao.updateSnoozeState(
            id = id,
            isSnoozed = true
        )
        Log.i("Abdooooo", "markAsSnoozed: markAsSnoozedState $markAsSnoozedState")
    }

    override suspend fun clearSnooze(id: Long) {
        val clearSnoozeState = reminderDao.updateSnoozeState(
            id = id,
            isSnoozed = false
        )
        Log.i("Abdooooo", "clearSnoozeState: clearSnoozeState $clearSnoozeState")
    }

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
    private fun trySchedulingReminder(reminder: Reminder): ReminderScheduleResult {
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