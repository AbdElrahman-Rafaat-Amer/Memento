package com.abdelrahman.raafat.memento.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class MemoDateTimeFormatter
    @Inject
    constructor() {
        private val formatter =
            DateTimeFormatter.ofPattern(
                REMINDER_DATE_TIME,
                Locale.getDefault()
            )

        private val shortFormatter =
            DateTimeFormatter.ofPattern(
                SHORT_REMINDER_TIME,
                Locale.getDefault()
            )

        fun format(
            date: Long,
            time: Long
        ): String {
            val localDate = LocalDate.ofEpochDay(date)
            val localTime = LocalTime.ofSecondOfDay(time)
            val localDateTime = LocalDateTime.of(localDate, localTime)
            return localDateTime.format(formatter)
        }

        fun format(dateTimeInMillis: Long): String {
            val localDateTime =
                Instant
                    .ofEpochMilli(dateTimeInMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            return localDateTime.format(shortFormatter)
        }

        fun parse(dateTime: String): Pair<Long, Long> {
            val localDateTime = LocalDateTime.parse(dateTime, formatter)
            val date = localDateTime.toLocalDate().toEpochDay()
            val time = localDateTime.toLocalTime().toSecondOfDay().toLong()
            return date to time
        }

        companion object {
            private const val REMINDER_DATE_TIME = "EEE, MM/dd/yyyy h:mm a"
            private const val SHORT_REMINDER_TIME = "EEE, h:mm a"
        }
    }
