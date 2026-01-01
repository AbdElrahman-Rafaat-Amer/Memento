package com.abdelrahman.raafat.memento.domain.model

data class Reminder(
    val id: Long,
    val title: String,
    val additionalInfo: String,
    val date: Long,
    val time: Long,
    val isDone: Boolean
)
