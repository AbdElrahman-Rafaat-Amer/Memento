package com.abdelrahman.raafat.memento.ui.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_reminders),
            style = AppTextStyles.textStyle28SPMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}



@ThemesPreviews
@Composable
fun EmptyScreenPreview(){
    MementoTheme {
        EmptyScreen()
    }
}