package com.abdelrahman.raafat.memento.domain.recurring

import com.abdelrahman.raafat.memento.domain.model.Recurrence
import java.time.Instant
import java.time.ZoneId

object RecurrenceCalculator {
    fun nextTrigger(
        currentTrigger: Long,
        recurrence: Recurrence
    ): Long? {
        if (recurrence == Recurrence.NONE) return null

        val zoned =
            Instant
                .ofEpochMilli(currentTrigger)
                .atZone(ZoneId.systemDefault())

        val next =
            when (recurrence) {
                Recurrence.DAILY -> zoned.plusDays(1)
                Recurrence.WEEKLY -> zoned.plusWeeks(1)
                Recurrence.MONTHLY -> zoned.plusMonths(1)
                else -> return null
            }

        return next.toInstant().toEpochMilli()
    }
}
