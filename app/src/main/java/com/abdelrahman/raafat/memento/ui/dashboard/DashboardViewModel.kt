package com.abdelrahman.raafat.memento.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.data.repository.ReminderRepository
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardReminderUi
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardUiState
import com.abdelrahman.raafat.memento.utils.DateTimeFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
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

    private var loadJob: Job? = null

    init {
        loadReminders()
    }

    private fun loadReminders() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            reminderRepository.getAllReminders()
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

}