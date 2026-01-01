package com.abdelrahman.raafat.memento.domain.model

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun Reminder.toTriggerMillis(): Long {
    val date = LocalDate.ofEpochDay(date)
    val time = LocalTime.ofSecondOfDay(time)
    return ZonedDateTime.of(date, time, ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}