package com.abdelrahman.raafat.memento.ui.remindereditor.model

import com.abdelrahman.raafat.memento.domain.model.Recurrence
import java.time.LocalDate
import java.time.LocalTime

data class ReminderEditorUiState(
    val title: String = "",
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val additionalInfo: String = "",
    val recurrence: Recurrence = Recurrence.NONE
)

val ReminderEditorUiState.isValid: Boolean
    get() = title.isNotBlank() && date != null && time != null
