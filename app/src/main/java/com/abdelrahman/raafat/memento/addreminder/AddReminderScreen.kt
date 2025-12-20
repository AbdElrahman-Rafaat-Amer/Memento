package com.abdelrahman.raafat.memento.addreminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.core.components.MEMOutlinedTextField
import com.abdelrahman.raafat.memento.core.components.MEMPrimaryButton
import com.abdelrahman.raafat.memento.core.components.MEMTobBar
import com.abdelrahman.raafat.memento.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.core.theme.ThemesPreviews

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

        MEMOutlinedTextField(
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

        MEMOutlinedTextField(
            value = state.additionalInfo,
            textStyle = AppTextStyles.textStyle16SPNormal,
            label = { Text(stringResource(R.string.additional_info)) },
            placeholder = { Text(stringResource(R.string.write_something)) },
            onValueChange = viewModel::onAdditionalInfo
        )

        MEMPrimaryButton(
            text = stringResource(R.string.save_reminder),
            isAllCaps = false,
            isEnabled = state.title.isNotBlank()
                    && state.date != null
                    && state.time != null,
            onButtonClicked = viewModel::saveReminder
        )
    }
}


@ThemesPreviews
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