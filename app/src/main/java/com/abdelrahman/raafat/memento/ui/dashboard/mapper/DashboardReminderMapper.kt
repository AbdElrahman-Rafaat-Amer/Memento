package com.abdelrahman.raafat.memento.ui.dashboard.mapper

import com.abdelrahman.raafat.memento.domain.model.Reminder
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardListItem.DashboardReminderUi
import com.abdelrahman.raafat.memento.utils.MemoDateTimeFormatter
import javax.inject.Inject

class DashboardReminderMapper
    @Inject
    constructor(
        private val dateTimeFormatter: MemoDateTimeFormatter
    ) {
        fun toUiModel(reminder: Reminder): DashboardReminderUi =
            DashboardReminderUi(
                id = reminder.id,
                title = reminder.title,
                additionalInfo = reminder.additionalInfo,
                dateTime = dateTimeFormatter.format(reminder.date, reminder.time),
                isDone = reminder.isDone,
                isSnoozed = reminder.isSnoozed,
                snoozedTime = dateTimeFormatter.format(reminder.snoozedTime),
                recurrence = reminder.recurrence
            )

        fun toDomain(uiModel: DashboardReminderUi): Reminder {
            val (date, time) = dateTimeFormatter.parse(uiModel.dateTime)
            return Reminder(
                id = uiModel.id,
                title = uiModel.title,
                additionalInfo = uiModel.additionalInfo,
                date = date,
                time = time,
                isDone = uiModel.isDone,
                isSnoozed = uiModel.isSnoozed,
                snoozedTime = -1,
                recurrence = uiModel.recurrence
            )
        }
    }
