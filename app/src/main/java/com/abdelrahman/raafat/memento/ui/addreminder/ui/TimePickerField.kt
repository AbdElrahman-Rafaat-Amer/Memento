package com.abdelrahman.raafat.memento.ui.addreminder.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.abdelrahman.raafat.memento.R
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(
    time: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit
) {
    val state = rememberTimePickerState()
    var show by remember { mutableStateOf(false) }

    if (show) {
        AlertDialog(
            onDismissRequest = { show = false },
            confirmButton = {
                TextButton(onClick = {
                    onTimeSelected(LocalTime.of(state.hour, state.minute))
                    show = false
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            text = {
                TimePicker(state = state)
            }
        )
    }

    OutlinedTextField(
        value = time?.toString() ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(stringResource(R.string.time)) },
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Press) {
                            show = true
                        }
                    }
                }
            },
        modifier = Modifier
            .fillMaxWidth()
    )
}
