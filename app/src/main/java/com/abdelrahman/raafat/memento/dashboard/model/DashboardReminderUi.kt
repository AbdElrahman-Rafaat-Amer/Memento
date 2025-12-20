package com.abdelrahman.raafat.memento.dashboard.model

data class DashboardReminderUi(
    val id: Long,
    val title: String,
    val additionalInfo: String,
    val dateTime: String,
    val isDone: Boolean
)