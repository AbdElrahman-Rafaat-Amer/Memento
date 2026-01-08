package com.abdelrahman.raafat.memento.ui.remindereditor

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ReminderEditorDestination
import com.abdelrahman.raafat.memento.domain.model.Reminder
import com.abdelrahman.raafat.memento.domain.repository.ReminderRepository
import com.abdelrahman.raafat.memento.domain.result.ReminderScheduleResult
import com.abdelrahman.raafat.memento.domain.validator.ReminderValidator
import com.abdelrahman.raafat.memento.domain.validator.ValidationResult
import com.abdelrahman.raafat.memento.ui.remindereditor.mapper.ReminderEditorMapper
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
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ReminderEditorViewModel
    @Inject
    constructor(
        private val reminderRepository: ReminderRepository,
        private val reminderValidator: ReminderValidator,
        private val editorMapper: ReminderEditorMapper,
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
            if (isEditMode && reminderId != null) {
                loadReminder(reminderId)
            } else if (isEditMode) {
                // Edit mode but no ID - show error
                viewModelScope.launch {
                    _uiEvent.send(ReminderEditorEvent.ShowError(R.string.something_went_wrong))
                }
            }
        }

        private fun loadReminder(reminderId: Long) {
            viewModelScope.launch {
                reminderRepository
                    .getReminderById(reminderId)
                    .catch { exception ->
                        Log.e(TAG, "loadReminder: Failed to load reminder", exception)
                        _uiEvent.send(ReminderEditorEvent.ShowError(R.string.something_went_wrong))
                    }.collect { reminder ->
                        _uiState.value = editorMapper.toUiState(reminder)
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

            val validationResult =
                reminderValidator.validate(
                    title = state.title,
                    date = state.date,
                    time = state.time
                )

            when (validationResult) {
                ValidationResult.Success -> {
                    viewModelScope.launch {
                        if (isEditMode) {
                            requireNotNull(reminderId) {
                                "Reminder ID cannot be null in edit mode"
                            }
                            updateReminder(editorMapper.toDomain(uiState = state, id = reminderId))
                        } else {
                            insertReminder(editorMapper.toDomain(uiState = state))
                        }
                    }
                }

                is ValidationResult.Error -> {
                    viewModelScope.launch {
                        _uiEvent.send(ReminderEditorEvent.ShowError(validationResult.messageResId))
                    }
                }
            }
        }

        private suspend fun updateReminder(reminder: Reminder) {
            val updateResult = reminderRepository.updateReminder(reminder)
            when (updateResult) {
                is ReminderScheduleResult.Success -> {
                    _uiEvent.send(ReminderEditorEvent.ReminderSaved)
                }

                is ReminderScheduleResult.ExactAlarmPermissionMissing -> {
                    _uiEvent.send(ReminderEditorEvent.ShowExactAlarmPermissionRequired)
                }

                is ReminderScheduleResult.PastTrigger -> {
                    _uiEvent.send(ReminderEditorEvent.ShowError(R.string.select_date_in_future))
                }

                is ReminderScheduleResult.DataBaseError,
                is ReminderScheduleResult.UnknownError
                -> {
                    _uiEvent.send(ReminderEditorEvent.ShowError(R.string.failed_to_update_reminder))
                }
            }
        }

        private suspend fun insertReminder(reminder: Reminder) {
            val insertResult = reminderRepository.insertReminder(reminder)
            when (insertResult) {
                is ReminderScheduleResult.Success -> {
                    _uiEvent.send(ReminderEditorEvent.ReminderSaved)
                }

                is ReminderScheduleResult.ExactAlarmPermissionMissing -> {
                    _uiEvent.send(ReminderEditorEvent.ShowExactAlarmPermissionRequired)
                }

                is ReminderScheduleResult.PastTrigger -> {
                    _uiEvent.send(ReminderEditorEvent.ShowError(R.string.select_date_in_future))
                }

                is ReminderScheduleResult.DataBaseError,
                is ReminderScheduleResult.UnknownError
                -> {
                    _uiEvent.send(ReminderEditorEvent.ShowError(R.string.failed_to_save_reminder))
                }
            }
        }

        companion object {
            private const val TAG = "ReminderEditorViewModel"
        }
    }
