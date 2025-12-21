package com.abdelrahman.raafat.memento.ui.addreminder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.raafat.memento.data.local.entity.ReminderEntity
import com.abdelrahman.raafat.memento.data.repository.ReminderRepository
import com.abdelrahman.raafat.memento.ui.addreminder.model.AddReminderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddReminderViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

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
        if (isValidReminder(state)) {
            val newReminder = ReminderEntity(
                title = state.title,
                date = state.date!!.toEpochDay(),
                time = state.time!!.toSecondOfDay().toLong(),
                additionalInfo = state.additionalInfo,
            )

            viewModelScope.launch {
                val insertResult = reminderRepository.insertReminder(newReminder)
                Log.i("Abdooooo", "saveReminder: insertResult $insertResult ")
            }
        }
    }

    private fun isValidReminder(state: AddReminderUiState) =
        state.title.isNotBlank() && state.date != null && state.time != null
}
