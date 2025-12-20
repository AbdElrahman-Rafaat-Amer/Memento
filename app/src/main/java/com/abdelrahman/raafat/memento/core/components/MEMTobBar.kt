package com.abdelrahman.raafat.memento.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.core.theme.ThemesPreviews

@Composable
fun MEMTobBar(
    title: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = AppTextStyles.textStyle16SPNormal.copy(textAlign = TextAlign.Center),
    iconVector: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack,
    iconColor: Color? = MaterialTheme.colorScheme.onBackground,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outlineVariant,
    borderWidth: Dp = 1.dp,
    onBackButtonClicked: () -> Unit = {},
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = borderColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderWidth.toPx()
                )
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        iconVector?.let {
            Image(
                imageVector = iconVector,
                colorFilter = iconColor?.let { ColorFilter.tint(iconColor) },
                contentDescription = stringResource(R.string.back),
                modifier = Modifier.clickable { onBackButtonClicked() }
            )
        }

        Text(
            text = title,
            style = textStyle.copy(color = textColor),
            modifier = Modifier.weight(1f),
        )
    }
}

@ThemesPreviews
@Composable
private fun MEMTobBarPreview() {
    MementoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            MEMTobBar(
                title = stringResource(R.string.new_reminder),
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            )
        }
    }
}