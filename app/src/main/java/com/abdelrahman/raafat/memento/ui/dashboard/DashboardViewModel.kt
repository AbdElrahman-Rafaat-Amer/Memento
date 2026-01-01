package com.abdelrahman.raafat.memento.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.domain.repository.ReminderRepository
import com.abdelrahman.raafat.memento.domain.result.ReminderScheduleResult
import com.abdelrahman.raafat.memento.ui.dashboard.mapper.DashboardReminderMapper
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardEvent
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardReminderUi
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val dashboardMapper: DashboardReminderMapper
) : ViewModel() {

    private val _dashboardUiState =
        MutableStateFlow(DashboardUiState(isLoading = true))
    val dashboardUiState = _dashboardUiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<DashboardEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var loadJob: Job? = null

    init {
        loadReminders()
    }

    private fun loadReminders() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            reminderRepository.getDashboardReminders()
                .map { entities ->
                    entities.map { dashboardMapper.toUiModel(it) }
                }
                .catch { exception ->
                    Log.e(TAG, "loadReminders: ${exception.message}", exception)
                    _dashboardUiState.value = DashboardUiState(
                        error = R.string.clear_app_data,
                        isLoading = false
                    )
                }.collect { remindersUi ->
                    _dashboardUiState.value = DashboardUiState(
                        reminders = remindersUi,
                        isLoading = false
                    )
                }
        }
    }

    fun retry() {
        _dashboardUiState.value = DashboardUiState(isLoading = true)
        loadReminders()
    }

    fun markReminderAsDone(dashboardReminderUi: DashboardReminderUi) {
        viewModelScope.launch {
            val updatedReminder = dashboardReminderUi.copy(isDone = true)
            val reminder = dashboardMapper.toDomain(updatedReminder)

            val updateReminderResult = reminderRepository.markReminderAsDone(reminder)
            when (updateReminderResult) {
                is ReminderScheduleResult.Success -> {
                    _uiEvent.emit(
                        DashboardEvent.ShowMarkAsDoneSuccess(
                            messageResId = R.string.marked_as_done,
                            reminder = updatedReminder
                        )
                    )
                }

                is ReminderScheduleResult.ExactAlarmPermissionMissing,
                is ReminderScheduleResult.PastTrigger -> {
                    //Shouldn't happen in MarkAsDone case as no time edit or schedule new reminder but will handle it anyway
                    _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_mark_as_done))
                }

                is ReminderScheduleResult.DataBaseError,
                is ReminderScheduleResult.UnknownError -> {
                    _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_mark_as_done))
                }
            }
        }
    }

    fun undoMarkingAsDone(dashboardReminderUi: DashboardReminderUi) {
        viewModelScope.launch {
            val updatedReminder = dashboardReminderUi.copy(isDone = true)
            val reminder = dashboardMapper.toDomain(updatedReminder)

            val updateReminderResult = reminderRepository.markReminderAsNotDone(reminder)
            when (updateReminderResult) {
                is ReminderScheduleResult.Success -> {
                    //Do nothing as the reminder will be rescheduled
                }

                is ReminderScheduleResult.ExactAlarmPermissionMissing -> {
                    _uiEvent.emit(DashboardEvent.ShowExactAlarmPermissionRequired)
                }

                is ReminderScheduleResult.PastTrigger -> {
                    //TODO enhance the message and handle the edge case if the user undo the reminder after it's time is past
                    _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_undo_action))
                }

                is ReminderScheduleResult.DataBaseError,
                is ReminderScheduleResult.UnknownError -> {
                    _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_undo_action))
                }
            }
        }
    }

    fun deleteReminder(dashboardReminderUi: DashboardReminderUi) {
        val reminder = dashboardMapper.toDomain(dashboardReminderUi)

        viewModelScope.launch {
            val deleteReminderResult = reminderRepository.softDeleteReminder(reminder)
            when (deleteReminderResult) {
                is ReminderScheduleResult.Success -> {
                    _uiEvent.emit(DashboardEvent.ShowDeleteSuccess(R.string.reminder_deleted_successfully))
                }

                is ReminderScheduleResult.ExactAlarmPermissionMissing,
                is ReminderScheduleResult.PastTrigger -> {
                    //Shouldn't happen in Delete case as no time edit or schedule new reminder but will handle it anyway
                    _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_delete_reminder))
                }

                is ReminderScheduleResult.DataBaseError,
                is ReminderScheduleResult.UnknownError -> {
                    _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_delete_reminder))
                }
            }
        }
    }

    companion object {
        private const val TAG = "DashboardViewModel"
    }
}