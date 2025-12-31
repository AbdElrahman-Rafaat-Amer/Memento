package com.abdelrahman.raafat.memento.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.domain.repository.ReminderRepository
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardEvent
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardReminderUi
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardUiState
import com.abdelrahman.raafat.memento.utils.DateTimeFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
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
                .map { entities -> entities.map(::mapToUiModel) }
                .catch { exception ->
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

    private fun mapToUiModel(entity: ReminderEntity): DashboardReminderUi {
        return DashboardReminderUi(
            id = entity.id,
            title = entity.title,
            additionalInfo = entity.additionalInfo,
            dateTime = formatDateTime(entity.date, entity.time),
            isDone = entity.isDone
        )
    }

    private fun formatDateTime(date: Long, time: Long): String {
        val localDate = LocalDate.ofEpochDay(date)
        val localTime = LocalTime.ofSecondOfDay(time)

        val localDateTime = LocalDateTime.of(localDate, localTime)
        val formatter =
            DateTimeFormatter.ofPattern(DateTimeFormats.REMINDER_DATE_TIME, Locale.getDefault())
        return localDateTime.format(formatter)
    }

    fun retry() {
        _dashboardUiState.value = DashboardUiState(isLoading = true)
        loadReminders()
    }

    fun markReminderAsDone(dashboardReminderUi: DashboardReminderUi) {
        viewModelScope.launch {
            val updatedReminder = dashboardReminderUi.copy(isDone = true)
            try {
                val reminderEntity = toEntity(reminderUi = updatedReminder)
                val updateReminderResult = reminderRepository.markReminderAsDone(reminderEntity)
                if (updateReminderResult) {
                    _uiEvent.emit(
                        DashboardEvent.ShowMarkAsDoneSuccess(
                            messageResId = R.string.marked_as_done,
                            reminder = updatedReminder
                        )
                    )
                } else {
                    _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_mark_as_done))
                }
            } catch (exception: Exception) {
                Log.e(TAG, "updateReminderAsDone: exception.message = ${exception.message}")
                _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_mark_as_done))
            }
        }
    }

    fun undoMarkingAsDone(dashboardReminderUi: DashboardReminderUi) {
        viewModelScope.launch {
            try {
                val reminderEntity = toEntity(reminderUi = dashboardReminderUi.copy(isDone = false))
                val updateReminderResult = reminderRepository.unDoMarkReminderAsDone(reminderEntity)
                if (updateReminderResult.not()) {
                    _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_undo_action))
                }
            } catch (exception: Exception) {
                Log.e(TAG, "updateReminderAsDone: exception.message = ${exception.message}")
                _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_undo_action))
            }
        }
    }

    fun deleteReminder(dashboardReminderUi: DashboardReminderUi) {
        val reminderEntity = toEntity(reminderUi = dashboardReminderUi)
        viewModelScope.launch {
            try {
                val deleteReminderResult = reminderRepository.softDeleteReminder(reminderEntity)
                if (deleteReminderResult) {
                    _uiEvent.emit(DashboardEvent.ShowDeleteSuccess(R.string.reminder_deleted_successfully))
                } else {
                    _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_delete_reminder))
                }
            } catch (exception: Exception) {
                Log.e(TAG, "deleteReminder: exception.message = ${exception.message}")
                _uiEvent.emit(DashboardEvent.ShowError(R.string.failed_to_delete_reminder))
            }
        }
    }

    private fun toEntity(
        reminderUi: DashboardReminderUi
    ): ReminderEntity {
        val (date, time) = parseDateTime(reminderUi.dateTime)
        val reminderEntity = ReminderEntity(
            id = reminderUi.id,
            title = reminderUi.title,
            date = date,
            time = time,
            additionalInfo = reminderUi.additionalInfo,
            isDone = reminderUi.isDone
        )
        return reminderEntity
    }

    private fun parseDateTime(dateTime: String): Pair<Long, Long> {
        val formatter = DateTimeFormatter.ofPattern(
            DateTimeFormats.REMINDER_DATE_TIME,
            Locale.getDefault()
        )

        val localDateTime = LocalDateTime.parse(dateTime, formatter)

        val date = localDateTime.toLocalDate().toEpochDay()
        val time = localDateTime.toLocalTime().toSecondOfDay().toLong()

        return date to time
    }

    companion object {
        private const val TAG = "DashboardViewModel"
    }
}