package com.abdelrahman.raafat.memento.ui.remindereditor

import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ReminderEditorDestination
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.domain.ReminderRepository
import com.abdelrahman.raafat.memento.ui.mapper.toEntity
import com.abdelrahman.raafat.memento.ui.mapper.toUiState
import com.abdelrahman.raafat.memento.ui.remindereditor.model.ReminderEditorEvent
import com.abdelrahman.raafat.memento.ui.remindereditor.model.ReminderEditorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ReminderEditorViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReminderEditorUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ReminderEditorEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val reminderId: Long? =
        savedStateHandle.get<Long>(ReminderEditorDestination.ARG_REMINDER_ID)

    val isEditMode: Boolean = reminderId != null

    val screenTitle =
        if (isEditMode) {
            R.string.edit_reminder
        } else {
            R.string.new_reminder
        }

    val buttonText =
        if (isEditMode) {
            R.string.update
        } else {
            R.string.save
        }

    init {
        reminderId?.let {
            loadReminder(it)
        } ?: {
            viewModelScope.launch {
                _uiEvent.send(ReminderEditorEvent.ShowError(R.string.something_went_wrong))
            }
        }
    }

    private fun loadReminder(reminderId: Long) {
        viewModelScope.launch {
            reminderRepository.getReminderById(reminderId)
                .catch { exception ->
                    Log.e(TAG, "loadReminder: Failed to load reminder", exception)
                    _uiEvent.send(ReminderEditorEvent.ShowError(R.string.something_went_wrong))
                }
                .collect { reminder ->
                    _uiState.value = reminder.toUiState()
                }
        }
    }


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
                if (isEditMode) {
                    requireNotNull(reminderId) {
                        "Reminder ID cannot be null in edit mode"
                    }
                    updateReminder(state.toEntity(reminderId))
                } else {
                    insertReminder(state.toEntity())
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

    private suspend fun updateReminder(entity: ReminderEntity) {
        val isSuccessUpdate = reminderRepository.updateReminder(entity)
        if (isSuccessUpdate) {
            _uiEvent.send(ReminderEditorEvent.ReminderSaved)
        } else {
            _uiEvent.send(ReminderEditorEvent.ShowError(R.string.failed_to_update_reminder))
        }
    }

    private suspend fun insertReminder(entity: ReminderEntity) {
        val isSuccessInsert = reminderRepository.insertReminder(entity)
        if (isSuccessInsert) {
            _uiEvent.send(ReminderEditorEvent.ReminderSaved)
        } else {
            _uiEvent.send(ReminderEditorEvent.ShowError(R.string.failed_to_save_reminder))
        }
    }

    companion object {
        private const val TAG = "ReminderEditorViewModel"
    }
}
