package com.abdelrahman.raafat.memento.ui.dashboard.model

import androidx.annotation.StringRes

sealed interface DashboardEvent {
    data class ShowSuccess(@StringRes val messageResId: Int) : DashboardEvent
    data class ShowError(@StringRes val messageResId: Int) : DashboardEvent

}