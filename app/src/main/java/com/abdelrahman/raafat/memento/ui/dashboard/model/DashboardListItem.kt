package com.abdelrahman.raafat.memento.ui.dashboard.model

import androidx.annotation.StringRes

sealed interface DashboardListItem {
    data class Section(
        @StringRes val titleResId: Int
    ) : DashboardListItem

    data class DashboardReminderUi(
        val id: Long,
        val title: String,
        val additionalInfo: String,
        val dateTime: String,
        val isDone: Boolean
    ) : DashboardListItem
}
