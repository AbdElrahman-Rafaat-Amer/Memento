package com.abdelrahman.raafat.memento.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdelrahman.raafat.memento.core.theme.ThemesPreviews
import com.abdelrahman.raafat.memento.core.theme.MementoTheme

@Composable
fun MEMCheckbox(
    text: String,
    isAllCaps: Boolean = false,
    isChecked: Boolean = true,
    onCheckedChange: (isChecked: Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(isChecked) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .padding(end = 12.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        checked = !checked
                        onCheckedChange(checked)
                    }
                )
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                onCheckedChange(it)
            }
        )
        Text(
            text =
                if (isAllCaps) {
                    text.uppercase()
                } else {
                    text
                },
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@ThemesPreviews
@Composable
private fun MEMCheckboxPreview() {
    MementoTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            MEMCheckbox("check Me") {
            }
        }
    }
}