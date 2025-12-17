package com.abdelrahman.raafat.memento.addreminder.model

import java.time.LocalDate
import java.time.LocalTime

data class ReminderUi(
    val title: String,
    val date: LocalDate,
    val time: LocalTime
)
