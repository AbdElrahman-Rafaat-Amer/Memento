package com.abdelrahman.raafat.memento.ui.remindereditor.mapper

import com.abdelrahman.raafat.memento.domain.model.Reminder
import com.abdelrahman.raafat.memento.ui.remindereditor.model.ReminderEditorUiState
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class ReminderEditorMapper
    @Inject
    constructor() {
        fun toUiState(reminder: Reminder): ReminderEditorUiState =
            ReminderEditorUiState(
                title = reminder.title,
                date = LocalDate.ofEpochDay(reminder.date),
                time = LocalTime.ofSecondOfDay(reminder.time),
                additionalInfo = reminder.additionalInfo
            )

        fun toDomain(
            uiState: ReminderEditorUiState,
            id: Long = 0L
        ): Reminder =
            Reminder(
                id = id,
                title = uiState.title,
                date =
                    requireNotNull(uiState.date) {
                        "Date cannot be null"
                    }.toEpochDay(),
                time =
                    requireNotNull(uiState.time) {
                        "Time cannot be null"
                    }.toSecondOfDay().toLong(),
                additionalInfo = uiState.additionalInfo,
                isDone = false
            )
    }
