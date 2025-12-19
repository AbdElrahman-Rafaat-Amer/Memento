package com.abdelrahman.raafat.memento.addreminder.model

import java.time.LocalDate
import java.time.LocalTime

data class AddReminderUiState(
    val title: String = "",
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val additionalInfo: String = "",
)