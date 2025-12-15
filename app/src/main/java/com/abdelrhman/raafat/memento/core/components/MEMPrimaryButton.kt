package com.abdelrhman.raafat.memento.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abdelrhman.raafat.memento.core.theme.ThemesPreviews
import com.abdelrhman.raafat.memento.core.theme.MementoTheme

@Composable
fun MEMPrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    isAllCaps: Boolean = true,
    isTextButton: Boolean = false,
    isEnabled: Boolean = true,
    onButtonClicked: () -> Unit
) {
    val buttonColors =
        if (isTextButton) {
            ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
            )
        } else {
            ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = MaterialTheme.colorScheme.onSurface
            )
        }

    val buttonBorders =
        if (isTextButton || isEnabled.not()) {
            null
        } else {
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        }

    val paddingValues =
        if (isTextButton) {
            PaddingValues(0.dp)
        } else {
            PaddingValues(15.dp)
        }

    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        modifier =
            modifier
                .fillMaxWidth()
                .apply {
                    if (isTextButton) {
                        Modifier
                    } else {
                        defaultMinSize(minHeight = 50.dp)
                    }
                },
        shape = RoundedCornerShape(size = 14.dp),
        colors = buttonColors,
        border = buttonBorders,
        contentPadding = paddingValues,
        enabled = isEnabled
    ) {
        Text(
            modifier = modifier,
            text =
                if (isAllCaps) {
                    text.uppercase()
                } else {
                    text
                },
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@ThemesPreviews
@Composable
private fun MEMPrimaryButtonPreview() {
    MementoTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(10.dp)
                .background(
                    MaterialTheme.colorScheme.background
                )
        ) {
            MEMPrimaryButton(
                text = "Next",
                onButtonClicked = {}
            )

            MEMPrimaryButton(
                text = "Skip",
                isTextButton = true,
                onButtonClicked = {}
            )

            MEMPrimaryButton(
                text = "Disabled",
                isEnabled = false,
                onButtonClicked = {}
            )
        }
    }
}