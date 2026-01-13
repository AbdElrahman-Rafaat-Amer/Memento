package com.abdelrahman.raafat.memento.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.core.components.EmptyScreen
import com.abdelrahman.raafat.memento.ui.core.components.ErrorScreen
import com.abdelrahman.raafat.memento.ui.core.components.LoadingScreen
import com.abdelrahman.raafat.memento.ui.core.components.MemoTobBar
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.dashboard.components.ReminderRow

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.historyUiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MemoTobBar(
            title = stringResource(R.string.history),
            textStyle = AppTextStyles.textStyle28SPMedium,
            titleModifier = Modifier.weight(1f),
            onBackButtonClicked = onBack
        )

        when {
            uiState.isLoading -> {
                LoadingScreen(modifier = Modifier.padding(horizontal = 16.dp))
            }

            uiState.reminders.isEmpty() -> {
                EmptyScreen(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    message = stringResource(R.string.no_history)
                )
            }

            uiState.error != null -> {
                ErrorScreen(
                    error = stringResource(uiState.error!!),
                    onRetry = {}
                )
            }

            else -> {
                LazyColumn {
                    items(uiState.reminders) { reminder ->
                        ReminderRow(
                            item = reminder,
                            modifier = Modifier.padding(horizontal = 20.dp),
                            isActionVisible = false
                        )
                    }
                }
            }
        }
    }
}
