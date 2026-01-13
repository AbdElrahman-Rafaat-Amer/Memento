package com.abdelrahman.raafat.memento.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.domain.repository.ReminderRepository
import com.abdelrahman.raafat.memento.ui.dashboard.mapper.DashboardReminderMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel
    @Inject
    constructor(
        private val repository: ReminderRepository,
        private val dashboardMapper: DashboardReminderMapper
    ) : ViewModel() {
        private val _historyUiState = MutableStateFlow(HistoryUiState())
        val historyUiState: StateFlow<HistoryUiState> = _historyUiState

        init {
            loadHistory()
        }

        private fun loadHistory() {
            viewModelScope.launch {
                repository
                    .getHistoryReminders()
                    .map { entities ->
                        entities.map { dashboardMapper.toUiModel(it) }
                    }.catch { exception ->
                        _historyUiState.value = HistoryUiState(isLoading = false, error = R.string.clear_app_data)
                    }.collect { items ->
                        _historyUiState.value = HistoryUiState(isLoading = false, reminders = items)
                    }
            }
        }
    }
