package com.abdelrahman.raafat.memento.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.core.components.LoadingScreen
import com.abdelrahman.raafat.memento.core.components.MEMTobBar
import com.abdelrahman.raafat.memento.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.core.theme.ThemesPreviews

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {

    val reminderUiState by dashboardViewModel.dashboardUiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MEMTobBar(
            title = stringResource(R.string.app_name),
            iconVector = null,
            isTitleCentered = false
        )

        when {
            reminderUiState.isLoading -> {
                LoadingScreen()
            }

            reminderUiState.error != null -> {
                ErrorScreen(
                    error = reminderUiState.error!!,
                    onRetry = {
                        dashboardViewModel.retry()
                    }
                )
            }

            reminderUiState.reminders.isEmpty() -> {
                EmptyScreen()
            }

            else -> {
                LazyColumn {
                    items(reminderUiState.reminders) {
                        ReminderItem(item = it)
                    }
                }
            }

        }

    }
}


@ThemesPreviews
@Composable
private fun DashboardScreenPreview() {
    MementoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            DashboardScreen()
        }

    }
}