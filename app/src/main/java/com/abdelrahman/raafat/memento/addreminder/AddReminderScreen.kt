package com.abdelrahman.raafat.memento.addreminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.core.components.MEMTobBar
import com.abdelrahman.raafat.memento.core.theme.MementoTheme

@OptIn(ExperimentalMaterial3Api::class)
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
        MEMTobBar(
            title = stringResource(R.string.new_reminder),
            onBackButtonClicked = onBack
        )

        Spacer(Modifier.height(12.dp))

        AddReminderContent(viewModel)
    }
}

@Composable
fun AddReminderContent(viewModel: AddReminderViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        OutlinedTextField(
            value = state.title,
            onValueChange = viewModel::onTitleChange,
            label = { Text(stringResource(R.string.title)) },
            modifier = Modifier.fillMaxWidth()
        )

        DatePickerField(
            date = state.date,
            onDateSelected = viewModel::onDateSelected
        )

        TimePickerField(
            time = state.time,
            onTimeSelected = viewModel::onTimeSelected
        )

        Button(
            onClick = viewModel::saveReminder,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.title.isNotBlank()
                    && state.date != null
                    && state.time != null
        ) {
            Text(stringResource(R.string.save_reminder))
        }

        if (state.reminders.isNotEmpty()) {
            Divider()
            Text("Saved (Preview)")
            state.reminders.forEach {
                Text("• ${it.title} at ${it.time}")
            }
        }
    }
}


@Preview
@Composable
fun AddReminderScreenPreview() {
    MementoTheme {
        AddReminderScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            onBack = {}
        )
    }
}