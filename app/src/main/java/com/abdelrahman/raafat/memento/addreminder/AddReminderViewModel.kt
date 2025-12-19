package com.abdelrahman.raafat.memento.addreminder

import androidx.lifecycle.ViewModel
import com.abdelrahman.raafat.memento.addreminder.model.AddReminderUiState
import com.abdelrahman.raafat.memento.addreminder.model.ReminderUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddReminderViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AddReminderUiState())
    val uiState = _uiState.asStateFlow()

    fun onTitleChange(value: String) {
        _uiState.update { it.copy(title = value) }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(date = date) }
    }

    fun onTimeSelected(time: LocalTime) {
        _uiState.update { it.copy(time = time) }
    }

    fun onAdditionalInfo(onAdditionalInfo: String) {
        _uiState.update { it.copy(additionalInfo = onAdditionalInfo) }
    }

    fun saveReminder() {
        val state = _uiState.value
        if (state.title.isBlank() || state.date == null || state.time == null) return

        val newReminder = ReminderUi(
            title = state.title,
            date = state.date,
            time = state.time
        )
        //TODO save in DataBase
    }
}
