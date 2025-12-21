package com.abdelrahman.raafat.memento.ui.dashboard.model

data class DashboardUiState(
    val reminders: List<DashboardReminderUi> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)