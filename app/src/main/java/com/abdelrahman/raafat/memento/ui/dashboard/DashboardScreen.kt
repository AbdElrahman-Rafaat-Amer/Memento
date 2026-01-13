package com.abdelrahman.raafat.memento.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
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
import com.abdelrahman.raafat.memento.ui.core.components.EmptyScreen
import com.abdelrahman.raafat.memento.ui.core.components.ErrorScreen
import com.abdelrahman.raafat.memento.ui.core.components.LoadingScreen
import com.abdelrahman.raafat.memento.ui.core.components.MemoFabButton
import com.abdelrahman.raafat.memento.ui.core.components.MemoTobBar
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews
import com.abdelrahman.raafat.memento.ui.dashboard.components.ReminderRow
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardEvent
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardListItem
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardListItem.DashboardReminderUi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    onAddClicked: () -> Unit,
    onUpdateClicked: (DashboardReminderUi) -> Unit,
    onHistoryClicked: () -> Unit
) {
    val reminderUiState by dashboardViewModel.dashboardUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        dashboardViewModel.uiEvent.collect { event ->
            snackbarHostState.currentSnackbarData?.dismiss()
            when (event) {
                is DashboardEvent.ShowMarkAsDoneSuccess -> {
                    scope.launch {
                        val snackbarResult =
                            snackbarHostState.showSnackbar(
                                message = context.getString(event.messageResId),
                                actionLabel = context.getString(R.string.undo),
                                duration = SnackbarDuration.Short
                            )
                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                            dashboardViewModel.undoMarkingAsDone(dashboardReminderUi = event.reminder)
                        }
                    }
                }

                is DashboardEvent.ShowDeleteSuccess -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(event.messageResId)
                        )
                    }
                }

                DashboardEvent.ShowExactAlarmPermissionRequired -> {
                    scope.launch {
                        snackbarHostState
                            .showSnackbar(
                                message = context.getString(R.string.exact_alarm_permission_required),
                                actionLabel = context.getString(R.string.open_settings),
                                duration = SnackbarDuration.Long
                            ).also { result ->
                                if (result == SnackbarResult.ActionPerformed) {
                                    context.startActivity(
                                        Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                                    )
                                }
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
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                MemoTobBar(
                    title = stringResource(R.string.app_name),
                    textStyle = AppTextStyles.textStyle28SPMedium,
                    iconVector = null,
                    titleModifier = Modifier.weight(1f),
                    endIcon = Icons.Default.Settings,
                    endIconAction = { onHistoryClicked() }
                )

                when {
                    reminderUiState.isLoading -> {
                        LoadingScreen(modifier = Modifier.padding(horizontal = 16.dp))
                    }

                    reminderUiState.error != null -> {
                        ErrorScreen(
                            error = stringResource(reminderUiState.error!!),
                            onRetry = {
                                dashboardViewModel.retry()
                            }
                        )
                    }

                    reminderUiState.reminders.isEmpty() -> {
                        EmptyScreen(
                            message = stringResource(R.string.no_reminders),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    else -> {
                        LazyColumn {
                            items(
                                items = reminderUiState.reminders
                            ) { item ->
                                when (item) {
                                    is DashboardListItem.Section -> {
                                        Spacer(Modifier.height(16.dp))
                                        Text(
                                            text = stringResource(item.titleResId),
                                            style =
                                                AppTextStyles.textStyle24SPBold.copy(
                                                    color = MaterialTheme.colorScheme.onSurface
                                                ),
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                    }

                                    is DashboardReminderUi -> {
                                        ReminderRow(
                                            item = item,
                                            modifier = Modifier.padding(horizontal = 20.dp),
                                            onDoneClicked = { reminderUi ->
                                                dashboardViewModel.markReminderAsDone(reminderUi)
                                            },
                                            onEditClicked = { reminderUi ->
                                                onUpdateClicked(reminderUi)
                                            },
                                            onDeleteClicked = { reminder ->
                                                dashboardViewModel.deleteReminder(reminder)
                                            }
                                        )
                                    }
                                }
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
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
        ) {
            DashboardScreen(
                onAddClicked = {},
                onUpdateClicked = {},
                onHistoryClicked = {}
            )
        }
    }
}
