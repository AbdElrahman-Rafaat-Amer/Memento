package com.abdelrhman.raafat.memento.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abdelrhman.raafat.memento.core.theme.ThemesPreviews
import com.abdelrhman.raafat.memento.core.theme.AppTextStyles
import com.abdelrhman.raafat.memento.core.theme.MementoTheme

@Composable
fun MEMCustomIndicator(
    color: Color,
    borderColor: Color = color,
    selectedColor: Color = color,
    size: Dp = 10.dp,
    text: String = "",
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle = AppTextStyles.textStyle28SPMedium,
    isSelected: Boolean = false,
    isClickable: Boolean = false,
    onNumberClick: (number: String) -> Unit = {}
) {
    Box(
        modifier =
            Modifier
                .padding(horizontal = 4.dp)
                .clip(CircleShape)
                .background(
                    color = if (isSelected) selectedColor else color
                ).size(size)
                .border(1.dp, borderColor, CircleShape)
                .then(
                    if (isClickable) {
                        Modifier.clickable { onNumberClick(text) }
                    } else {
                        Modifier
                    }
                ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}


@ThemesPreviews
@Composable
private fun MEMCustomIndicatorPreview() {
    MementoTheme {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            MEMCustomIndicator(
                color = MaterialTheme.colorScheme.primary,
                size = 70.dp,
                text = "2"
            )

            MEMCustomIndicator(
                color = MaterialTheme.colorScheme.primary,
                size = 70.dp,
                text = "4",
                selectedColor = MaterialTheme.colorScheme.background,
                isSelected = true,
                isClickable = true
            )
        }
    }
}