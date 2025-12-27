package com.abdelrahman.raafat.memento.ui.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.remindereditor.components.DatePickerField
import com.abdelrahman.raafat.memento.ui.remindereditor.components.TimePickerField
import com.abdelrahman.raafat.memento.ui.remindereditor.model.ReminderEditorUiState
import com.abdelrahman.raafat.memento.ui.remindereditor.model.isValid
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun ReminderContent(
    reminderItem: ReminderEditorUiState,
    buttonText: String,
    onTitleChanged: (String) -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onTimeChanged: (LocalTime) -> Unit,
    onAdditionalInfoChanged: (String) -> Unit,
    onSaveButtonClicked: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        MemoOutlinedTextField(
            value = reminderItem.title,
            textStyle = AppTextStyles.textStyle16SPNormal,
            label = { Text(stringResource(R.string.title)) },
            placeholder = { Text(stringResource(R.string.enter_title)) },
            onValueChange = {
                onTitleChanged(it)
            }
        )

        DatePickerField(
            date = reminderItem.date,
            onDateSelected = {
                onDateChanged(it)
            }
        )

        TimePickerField(
            time = reminderItem.time,
            onTimeSelected = {
                onTimeChanged(it)
            }
        )

        MemoOutlinedTextField(
            value = reminderItem.additionalInfo,
            textStyle = AppTextStyles.textStyle16SPNormal,
            label = { Text(stringResource(R.string.additional_info)) },
            placeholder = { Text(stringResource(R.string.write_something)) },
            onValueChange = {
                onAdditionalInfoChanged(it)
            }
        )

        MemoPrimaryButton(
            text = buttonText,
            isAllCaps = false,
            isEnabled = reminderItem.isValid,
            onButtonClicked = {
                focusManager.clearFocus()
                onSaveButtonClicked()
            }
        )
    }
}

@ThemesPreviews
@Composable
private fun ReminderContentPreview() {
    MementoTheme {
        ReminderContent(
            reminderItem = ReminderEditorUiState(
                title = "Test Reminder",
                date = LocalDate.ofEpochDay(2000),
                time = LocalTime.ofSecondOfDay(4000),
                additionalInfo = "This is test reminder",
            ),
            buttonText = "Save",
            onTitleChanged = {},
            onDateChanged = {},
            onTimeChanged = {},
            onAdditionalInfoChanged = {},
            onSaveButtonClicked = { {} }
        )
    }
}