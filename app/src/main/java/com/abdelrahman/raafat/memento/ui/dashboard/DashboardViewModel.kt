package com.abdelrahman.raafat.memento.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.domain.ReminderRepository
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
                        error = exception.message ?: "Unknown error occurred",
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
        val updatedReminder = dashboardReminderUi.copy(isDone = true)
        updateReminderAsDone(
            dashboardReminderUi = dashboardReminderUi,
            isDone = true,
            successEvent = DashboardEvent.ShowMarkAsDoneSuccess(
                messageResId = R.string.marked_as_done,
                reminder = updatedReminder
            ),
            failedEvent = DashboardEvent.ShowError(R.string.failed_to_mark_as_done)
        )
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

    fun undoMarkingAsDone(dashboardReminderUi: DashboardReminderUi) {
        updateReminderAsDone(
            dashboardReminderUi = dashboardReminderUi,
            isDone = false,
            successEvent = null,
            failedEvent = DashboardEvent.ShowError(R.string.failed_to_undo_action)
        )
    }

    private fun updateReminderAsDone(
        dashboardReminderUi: DashboardReminderUi,
        isDone: Boolean,
        successEvent: DashboardEvent.ShowMarkAsDoneSuccess?,
        failedEvent: DashboardEvent.ShowError,
    ) {
        viewModelScope.launch {
            try {
                val (date, time) = parseDateTime(dashboardReminderUi.dateTime)
                val reminderEntity = ReminderEntity(
                    id = dashboardReminderUi.id,
                    title = dashboardReminderUi.title,
                    date = date,
                    time = time,
                    additionalInfo = dashboardReminderUi.additionalInfo,
                    isDone = isDone
                )
                val updateReminderResult = reminderRepository.updateReminder(reminderEntity)
                if (updateReminderResult) {
                    successEvent?.let {
                        _uiEvent.emit(successEvent)
                    }
                } else {
                    _uiEvent.emit(failedEvent)
                }
            } catch (exception: Exception) {
                Log.e(TAG, "updateReminderAsDone: exception.message = ${exception.message}")
                _uiEvent.emit(failedEvent)
            }
        }
    }

    companion object {
        private const val TAG = "DashboardViewModel"
    }
}