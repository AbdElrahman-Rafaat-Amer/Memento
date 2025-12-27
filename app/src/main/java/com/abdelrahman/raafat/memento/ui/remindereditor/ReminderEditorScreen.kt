package com.abdelrahman.raafat.memento.ui.remindereditor

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.remindereditor.model.ReminderEditorEvent
import com.abdelrahman.raafat.memento.ui.core.components.MemoTobBar
import com.abdelrahman.raafat.memento.ui.core.components.ReminderContent
import kotlinx.coroutines.launch

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun AddReminderScreen(
    modifier: Modifier = Modifier,
    editorViewModel: ReminderEditorViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by editorViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        editorViewModel.uiEvent.collect { event ->
            when (event) {
                is ReminderEditorEvent.ReminderSaved -> {
                    onBack()
                }

                is ReminderEditorEvent.ShowError -> {
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
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

    DisposableEffect(Unit) {
        onDispose {
            snackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val screenTitle =
                if (editorViewModel.isEditMode) {
                    stringResource(R.string.edit_reminder)
                } else {
                    stringResource(R.string.new_reminder)
                }

            MemoTobBar(
                title = screenTitle,
                onBackButtonClicked = onBack
            )

            val buttonText =
                if (editorViewModel.isEditMode) {
                    stringResource(R.string.update)
                } else {
                    stringResource(R.string.save)
                }

            ReminderContent(
                reminderItem = state,
                buttonText = buttonText,
                onTitleChanged = { newTitle ->
                    editorViewModel.onTitleChange(newTitle)
                },
                onDateChanged = { newDate ->
                    editorViewModel.onDateSelected(newDate)
                },
                onTimeChanged = { newTime ->
                    editorViewModel.onTimeSelected(newTime)
                },
                onAdditionalInfoChanged = { newInfo ->
                    editorViewModel.onAdditionalInfo(newInfo)
                },
                onSaveButtonClicked = {
                    editorViewModel.saveReminder()
                }
            )
        }
    }
}