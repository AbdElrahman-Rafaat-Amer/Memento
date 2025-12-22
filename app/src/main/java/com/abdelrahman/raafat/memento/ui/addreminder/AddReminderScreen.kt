package com.abdelrahman.raafat.memento.ui.addreminder

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.addreminder.model.AddReminderEvent
import com.abdelrahman.raafat.memento.ui.addreminder.ui.DatePickerField
import com.abdelrahman.raafat.memento.ui.addreminder.ui.TimePickerField
import com.abdelrahman.raafat.memento.ui.core.components.MemoOutlinedTextField
import com.abdelrahman.raafat.memento.ui.core.components.MemoPrimaryButton
import com.abdelrahman.raafat.memento.ui.core.components.MemoTobBar
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews
import kotlinx.coroutines.launch

@Composable
fun AddReminderScreen(
    modifier: Modifier = Modifier,
    viewModel: AddReminderViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MemoTobBar(
            title = stringResource(R.string.new_reminder),
            onBackButtonClicked = onBack
        )

        Spacer(Modifier.height(20.dp))

        AddReminderContent(viewModel, onBack)
    }
}

@Composable
fun AddReminderContent(
    viewModel: AddReminderViewModel,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AddReminderEvent.ReminderSaved -> {
                    onBack()
                }

                is AddReminderEvent.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = getString(
                                context = context,
                                messageResId = event.messageResId
                            ),
                            actionLabel = getString(context = context, messageResId = R.string.ok),
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }
        }
    }

    // Clean up on dispose
    DisposableEffect(Unit) {
        onDispose {
            snackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            MemoOutlinedTextField(
                value = state.title,
                textStyle = AppTextStyles.textStyle16SPNormal,
                label = { Text(stringResource(R.string.title)) },
                placeholder = { Text(stringResource(R.string.enter_title)) },
                onValueChange = viewModel::onTitleChange
            )

            DatePickerField(
                date = state.date,
                onDateSelected = viewModel::onDateSelected
            )

            TimePickerField(
                time = state.time,
                onTimeSelected = viewModel::onTimeSelected
            )

            MemoOutlinedTextField(
                value = state.additionalInfo,
                textStyle = AppTextStyles.textStyle16SPNormal,
                label = { Text(stringResource(R.string.additional_info)) },
                placeholder = { Text(stringResource(R.string.write_something)) },
                onValueChange = viewModel::onAdditionalInfo
            )

            MemoPrimaryButton(
                text = stringResource(R.string.save_reminder),
                isAllCaps = false,
                isEnabled = state.title.isNotBlank()
                        && state.date != null
                        && state.time != null,
                onButtonClicked = viewModel::saveReminder
            )
        }
    }
}


private fun getString(context: Context, messageResId: Int): String = context.getString(messageResId)

@ThemesPreviews
@Composable
private fun AddReminderScreenPreview() {
    MementoTheme {
        AddReminderScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            onBack = {}
        )
    }
}