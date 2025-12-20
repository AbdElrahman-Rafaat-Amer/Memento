package com.abdelrahman.raafat.memento.dashboard

import androidx.lifecycle.ViewModel
import com.abdelrahman.raafat.memento.dashboard.model.DashboardUiState
import com.abdelrahman.raafat.memento.data.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val _dashboardUiState = MutableStateFlow(DashboardUiState())
    val dashboardUiState = _dashboardUiState.asStateFlow()


    init {
        loadReminders()
    }

    private fun loadReminders() {
        _dashboardUiState.value = DashboardUiState(reminders = emptyList(), isLoading = true)
    }
}