package com.abdelrahman.raafat.memento.ui.remindereditor.model

import androidx.annotation.StringRes

sealed interface ReminderEditorEvent {
    data object ReminderSaved : ReminderEditorEvent
    data class ShowError(@StringRes val messageResId: Int) : ReminderEditorEvent
}