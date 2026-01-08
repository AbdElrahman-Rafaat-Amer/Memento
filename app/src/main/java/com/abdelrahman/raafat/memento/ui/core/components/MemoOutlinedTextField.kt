package com.abdelrahman.raafat.memento.ui.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews

@Composable
fun MemoOutlinedTextField(
    value: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    isEnabled: Boolean = true,
    isClickable: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeholder: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier =
            modifier
                .fillMaxWidth()
                .border(1.dp, Color.Transparent, RoundedCornerShape(160.dp))
                .clickable(enabled = isClickable) { onClick.invoke() },
        enabled = isEnabled,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@ThemesPreviews
@Composable
private fun MemoOutlinedTextFieldPreview() {
    MementoTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier =
                Modifier
                    .padding(10.dp)
                    .background(
                        MaterialTheme.colorScheme.background
                    )
        ) {
            MemoOutlinedTextField(
                value = stringResource(R.string.write_something),
                textStyle = AppTextStyles.textStyle16SPNormal,
                label = { Text(stringResource(R.string.additional_info)) },
                onValueChange = { },
                onClick = {}
            )
        }
    }
}
