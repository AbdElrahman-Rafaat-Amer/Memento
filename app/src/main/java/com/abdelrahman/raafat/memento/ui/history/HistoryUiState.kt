package com.abdelrahman.raafat.memento.ui.history

import androidx.annotation.StringRes
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardListItem

data class HistoryUiState(
    val reminders: List<DashboardListItem.DashboardReminderUi> = emptyList(),
    @StringRes val error: Int? = null,
    val isLoading: Boolean = true
)
