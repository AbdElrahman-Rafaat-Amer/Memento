package com.abdelrahman.raafat.memento.ui.remindereditor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.domain.model.Recurrence
import com.abdelrahman.raafat.memento.ui.core.components.MemoOutlinedTextField
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepeatPicker(
    selected: Recurrence,
    onSelected: (Recurrence) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        MemoOutlinedTextField(
            value = stringResource(selected.displayNameRes),
            onValueChange = {},
            modifier =
                Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
            readOnly = true,
            label = { Text(stringResource(R.string.repeat)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            textStyle = AppTextStyles.textStyle16SPNormal
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Recurrence.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(stringResource(option.displayNameRes)) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@ThemesPreviews
@Composable
private fun RepeatPickerPreview() {
    MementoTheme {
        RepeatPicker(
            modifier = Modifier.fillMaxSize(),
            selected = Recurrence.NONE,
            onSelected = {}
        )
    }
}
