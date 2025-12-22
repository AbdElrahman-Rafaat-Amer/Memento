package com.abdelrahman.raafat.memento.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.core.components.LoadingScreen
import com.abdelrahman.raafat.memento.ui.core.components.MemoFabButton
import com.abdelrahman.raafat.memento.ui.core.components.MemoTobBar
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    onAddClicked: () -> Unit
) {
    val reminderUiState by dashboardViewModel.dashboardUiState.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            MemoTobBar(
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

        MemoFabButton(
            iconResID = android.R.drawable.ic_input_add,
            contentDescription = stringResource(R.string.add),
            modifier = Modifier.size(70.dp),
            iconModifier = Modifier.size(50.dp),
            onFabClick = onAddClicked
        )
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
            DashboardScreen{}
        }
    }
}