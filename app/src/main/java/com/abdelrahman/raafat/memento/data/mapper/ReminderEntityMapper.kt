package com.abdelrahman.raafat.memento.data.mapper

import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.domain.model.Reminder
import com.abdelrahman.raafat.memento.domain.model.toTriggerMillis
import javax.inject.Inject

class ReminderEntityMapper
    @Inject
    constructor() {
        fun toDomain(entity: ReminderEntity): Reminder =
            Reminder(
                id = entity.id,
                title = entity.title,
                additionalInfo = entity.additionalInfo,
                date = entity.date,
                time = entity.time,
                isDone = entity.isDone,
                isSnoozed = entity.isSnoozed,
                snoozedTime = entity.triggerAtMillis,
                recurrence = entity.recurrence
            )

        fun toEntity(reminder: Reminder): ReminderEntity =
            ReminderEntity(
                id = reminder.id,
                title = reminder.title,
                additionalInfo = reminder.additionalInfo,
                date = reminder.date,
                time = reminder.time,
                isDone = reminder.isDone,
                triggerAtMillis = reminder.toTriggerMillis(),
                recurrence = reminder.recurrence
            )

        fun toDomainList(entities: List<ReminderEntity>): List<Reminder> = entities.map(::toDomain)
    }
