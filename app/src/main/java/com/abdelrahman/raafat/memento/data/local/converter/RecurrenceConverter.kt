package com.abdelrahman.raafat.memento.data.local.converter

import androidx.room.TypeConverter
import com.abdelrahman.raafat.memento.domain.model.Recurrence

class RecurrenceConverter {
    @TypeConverter
    fun fromRecurrence(value: Recurrence): String = value.name

    @TypeConverter
    fun toRecurrence(value: String): Recurrence = Recurrence.valueOf(value)
}
