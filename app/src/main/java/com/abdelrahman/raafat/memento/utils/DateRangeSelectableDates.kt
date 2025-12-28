package com.abdelrahman.raafat.memento.utils

import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Calendar

object DateRangeSelectableDates : SelectableDates {
    private val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    // Using LocalDate for clear date handling (without timezone confusion)
    private val todayLocalDate = LocalDate.now()

    // Convert LocalDate to UTC milliseconds at start of day
    val todayUtcMillis = todayLocalDate
        .atStartOfDay()
        .toInstant(ZoneOffset.UTC)
        .toEpochMilli()

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= today
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= LocalDate.now().year
    }
}
