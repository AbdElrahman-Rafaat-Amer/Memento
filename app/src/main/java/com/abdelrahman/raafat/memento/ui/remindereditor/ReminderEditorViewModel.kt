package com.abdelrahman.raafat.memento.ui.remindereditor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.domain.ReminderRepository
import com.abdelrahman.raafat.memento.ui.remindereditor.model.ReminderEditorEvent
import com.abdelrahman.raafat.memento.ui.remindereditor.model.ReminderEditorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ReminderEditorViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReminderEditorUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ReminderEditorEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onTitleChange(value: String) {
        _uiState.update { it.copy(title = value) }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(date = date) }
    }

    fun onTimeSelected(time: LocalTime) {
        _uiState.update { it.copy(time = time) }
    }

    fun onAdditionalInfo(additionalInfo: String) {
        _uiState.update { it.copy(additionalInfo = additionalInfo) }
    }

    fun saveReminder() {
        val state = _uiState.value

        val validationError = validateReminder(state)
        if (validationError != null) {
            viewModelScope.launch {
                _uiEvent.send(ReminderEditorEvent.ShowError(validationError))
            }
            return
        }

        viewModelScope.launch {
            try {
                val newReminder = ReminderEntity(
                    title = state.title,
                    date = state.date!!.toEpochDay(),
                    time = state.time!!.toSecondOfDay().toLong(),
                    additionalInfo = state.additionalInfo,
                )
                val isSuccessInsert = reminderRepository.insertReminder(newReminder)
                if (isSuccessInsert) {
                    _uiEvent.send(ReminderEditorEvent.ReminderSaved)
                } else {
                    _uiEvent.send(ReminderEditorEvent.ShowError(R.string.failed_to_save_reminder))
                }
            } catch (exception: Exception) {
                Log.e(TAG, "saveReminder: exception.messageResId = ${exception.message}")
                _uiEvent.send(ReminderEditorEvent.ShowError(R.string.something_went_wrong))
            }
        }
    }


    private fun validateReminder(state: ReminderEditorUiState): Int? {
        return when {
            state.title.isBlank() -> R.string.title_required
            state.date == null -> R.string.date_required
            state.time == null -> R.string.time_required
            else -> {
                val selectedDateTime = LocalDateTime.of(state.date, state.time)
                if (selectedDateTime.isBefore(LocalDateTime.now())) {
                    R.string.select_date_in_future
                } else {
                    null
                }
            }
        }
    }

    companion object {
        private const val TAG = "AddReminderViewModel"
    }
}
