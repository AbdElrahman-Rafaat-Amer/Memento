package com.abdelrahman.raafat.memento.data.mapper

import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.domain.model.Reminder
import com.abdelrahman.raafat.memento.domain.model.toTriggerMillis
import javax.inject.Inject

class ReminderEntityMapper @Inject constructor() {

    fun toDomain(entity: ReminderEntity): Reminder {
        return Reminder(
            id = entity.id,
            title = entity.title,
            additionalInfo = entity.additionalInfo,
            date = entity.date,
            time = entity.time,
            isDone = entity.isDone
        )
    }

    fun toEntity(reminder: Reminder): ReminderEntity {
        return ReminderEntity(
            id = reminder.id,
            title = reminder.title,
            additionalInfo = reminder.additionalInfo,
            date = reminder.date,
            time = reminder.time,
            isDone = reminder.isDone,
            triggerAtMillis = reminder.toTriggerMillis()
        )
    }

    fun toDomainList(entities: List<ReminderEntity>): List<Reminder> {
        return entities.map(::toDomain)
    }
}