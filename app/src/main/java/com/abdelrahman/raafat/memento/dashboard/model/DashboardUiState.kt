package com.abdelrahman.raafat.memento.dashboard.model

import java.time.LocalDate
import java.time.LocalTime
data class DashboardUiState(
    val reminders: List<DashboardReminderUi> = emptyList(),
    val isLoading: Boolean = false
)