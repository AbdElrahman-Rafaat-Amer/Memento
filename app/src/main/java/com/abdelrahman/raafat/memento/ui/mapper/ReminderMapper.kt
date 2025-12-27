package com.abdelrahman.raafat.memento.ui.mapper


import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.ui.remindereditor.model.ReminderEditorUiState
import java.time.LocalDate
import java.time.LocalTime

fun ReminderEntity.toUiState(): ReminderEditorUiState {
    return ReminderEditorUiState(
        title = title,
        date = LocalDate.ofEpochDay(date),
        time = LocalTime.ofSecondOfDay(time),
        additionalInfo = additionalInfo
    )
}

fun ReminderEditorUiState.toEntity(
    id: Long = 0L
): ReminderEntity {
    return ReminderEntity(
        id = id,
        title = title,
        date = date!!.toEpochDay(),
        time = time!!.toSecondOfDay().toLong(),
        additionalInfo = additionalInfo
    )
}
