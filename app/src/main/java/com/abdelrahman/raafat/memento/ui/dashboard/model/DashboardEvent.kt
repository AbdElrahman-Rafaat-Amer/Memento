package com.abdelrahman.raafat.memento.ui.dashboard.model

import androidx.annotation.StringRes

sealed interface DashboardEvent {
    data class ShowMarkAsDoneSuccess(
        @StringRes val messageResId: Int,
        val reminder: DashboardReminderUi
    ) : DashboardEvent

    data class ShowError(@StringRes val messageResId: Int) : DashboardEvent

}