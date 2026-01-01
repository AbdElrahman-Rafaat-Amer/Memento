package com.abdelrahman.raafat.memento.domain.validator

import com.abdelrahman.raafat.memento.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class ReminderValidator @Inject constructor() {
    fun validate(
        title: String,
        date: LocalDate?,
        time: LocalTime?
    ): ValidationResult {
        return when {
            title.isBlank() -> ValidationResult.Error(R.string.title_required)
            date == null -> ValidationResult.Error(R.string.date_required)
            time == null -> ValidationResult.Error(R.string.time_required)
            isDateTimeInPast(date, time) ->
                ValidationResult.Error(R.string.select_date_in_future)

            else -> ValidationResult.Success
        }
    }

    private fun isDateTimeInPast(date: LocalDate, time: LocalTime): Boolean {
        val selectedDateTime = LocalDateTime.of(date, time)
        return selectedDateTime.isBefore(LocalDateTime.now())
    }

}