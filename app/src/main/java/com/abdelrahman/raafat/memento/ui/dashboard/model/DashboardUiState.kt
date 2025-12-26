package com.abdelrahman.raafat.memento.ui.dashboard.model

import androidx.annotation.StringRes

data class DashboardUiState(
    val reminders: List<DashboardReminderUi> = emptyList(),
    @StringRes val error: Int? = null,
    val isLoading: Boolean = false
)