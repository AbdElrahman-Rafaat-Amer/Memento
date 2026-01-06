package com.abdelrahman.raafat.memento.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ReminderEntity")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val date: Long,
    val time: Long,
    val additionalInfo: String,
    val isDone: Boolean = false,
    val isDeleted: Boolean = false,
    val deletedAt: Long? = null,
    val isSnoozed: Boolean = false,
    val triggerAtMillis: Long
)