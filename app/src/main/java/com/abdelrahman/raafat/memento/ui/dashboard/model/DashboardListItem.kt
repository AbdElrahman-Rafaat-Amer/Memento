package com.abdelrahman.raafat.memento.ui.dashboard.model

import androidx.annotation.StringRes
import com.abdelrahman.raafat.memento.domain.model.Recurrence

sealed interface DashboardListItem {
    data class Section(
        @StringRes val titleResId: Int
    ) : DashboardListItem

    data class DashboardReminderUi(
        val id: Long,
        val title: String,
        val additionalInfo: String,
        val dateTime: String,
        val isDone: Boolean,
        val isSnoozed: Boolean,
        val snoozedTime: String,
        val recurrence: Recurrence
    ) : DashboardListItem
}
