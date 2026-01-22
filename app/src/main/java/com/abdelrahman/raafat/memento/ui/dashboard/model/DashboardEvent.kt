package com.abdelrahman.raafat.memento.ui.dashboard.model

import androidx.annotation.StringRes

sealed interface DashboardEvent {
    data class ShowMarkAsDoneSuccess(
        @StringRes val messageResId: Int,
        val reminder: DashboardListItem.DashboardReminderUi
    ) : DashboardEvent

    data class ShowDeleteSuccess(
        @StringRes val messageResId: Int
    ) : DashboardEvent

    data object ShowExactAlarmPermissionRequired : DashboardEvent

    data class ShowError(
        @StringRes val messageResId: Int
    ) : DashboardEvent
}
