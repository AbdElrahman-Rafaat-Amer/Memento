package com.abdelrahman.raafat.memento.ui.remindereditor.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.utils.DateRangeSelectableDates
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    date: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = DateRangeSelectableDates.todayUtcMillis,
            initialDisplayedMonthMillis = DateRangeSelectableDates.todayUtcMillis,
            selectableDates = DateRangeSelectableDates
        )
    var show by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    if (show) {
        DatePickerDialog(
            onDismissRequest = { show = false },
            confirmButton = {
                TextButton(
                    enabled = datePickerState.selectedDateMillis != null,
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(
                                Instant
                                    .ofEpochMilli(it)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            )
                        }
                        show = false
                    }
                ) {
                    Text(stringResource(R.string.set))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    OutlinedTextField(
        value = date?.format(dateFormatter) ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(stringResource(R.string.date)) },
        placeholder = { Text(stringResource(R.string.select_date)) },
        interactionSource =
            remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Press) {
                                show = true
                            }
                        }
                    }
                },
        modifier = Modifier.fillMaxWidth()
    )
}
