package com.abdelrahman.raafat.memento.ui.addreminder.model

import androidx.annotation.StringRes

sealed interface AddReminderEvent {
    data object ReminderSaved : AddReminderEvent
    data class ShowError(@StringRes val messageResId: Int) : AddReminderEvent
}


val AddReminderUiState.isValid: Boolean
    get() = title.isNotBlank() && date != null && time != null