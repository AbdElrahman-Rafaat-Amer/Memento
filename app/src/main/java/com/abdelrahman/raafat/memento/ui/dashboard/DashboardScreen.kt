package com.abdelrahman.raafat.memento.ui.dashboard

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.core.components.LoadingScreen
import com.abdelrahman.raafat.memento.ui.core.components.MemoFabButton
import com.abdelrahman.raafat.memento.ui.core.components.MemoTobBar
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews
import com.abdelrahman.raafat.memento.ui.core.components.EmptyScreen
import com.abdelrahman.raafat.memento.ui.core.components.ErrorScreen
import com.abdelrahman.raafat.memento.ui.dashboard.components.ReminderRow
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardEvent
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    onAddClicked: () -> Unit
) {
    val reminderUiState by dashboardViewModel.dashboardUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        dashboardViewModel.uiEvent.collect { event ->
            when (event) {
                is DashboardEvent.ShowMarkAsDoneSuccess -> {
                    scope.launch {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            message = context.getString(event.messageResId),
                            actionLabel = context.getString(R.string.undo),
                            duration = SnackbarDuration.Short
                        )
                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                            dashboardViewModel.undoMarkingAsDone(dashboardReminderUi = event.reminder)
                        }
                    }
                }

                is DashboardEvent.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(event.messageResId),
                            actionLabel = context.getString(R.string.ok),
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                MemoTobBar(
                    title = stringResource(R.string.app_name),
                    textStyle = AppTextStyles.textStyle28SPMedium,
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
                            items(
                                items = reminderUiState.reminders,
                                key = { reminder -> reminder.id }
                            ) { reminder ->
                                ReminderRow(
                                    item = reminder,
                                    onDoneClicked = { reminderUi ->
                                        dashboardViewModel.markReminderAsDone(reminderUi)
                                    },
                                    onEditClicked = {
                                        //TODO implement Edit later
                                    },
                                    onDeleteClicked = {
                                        //TODO implement Delete later
                                    }
                                )
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
            DashboardScreen {}
        }
    }
}