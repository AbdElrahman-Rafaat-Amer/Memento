package com.abdelrahman.raafat.memento.data.local.entity

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun ReminderEntity.toTriggerMillis(): Long {
    val date = LocalDate.ofEpochDay(date)
    val time = LocalTime.ofSecondOfDay(time)
    return ZonedDateTime.of(date, time, ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}
