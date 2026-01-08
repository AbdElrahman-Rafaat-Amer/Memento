package com.abdelrahman.raafat.memento.ui.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews

@Composable
fun ErrorScreen(
    error: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {}
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = error,
            style =
                AppTextStyles.textStyle28SPMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
        )

        Spacer(Modifier.height((32.dp)))

        MemoPrimaryButton(
            text = stringResource(R.string.retry),
            onButtonClicked = onRetry
        )
    }
}

@ThemesPreviews
@Composable
private fun ErrorScreenPreview() {
    MementoTheme {
        ErrorScreen(error = "error")
    }
}
