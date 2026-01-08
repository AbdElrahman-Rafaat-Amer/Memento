package com.abdelrahman.raafat.memento.ui.notification

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.abdelrahman.raafat.memento.R

@Composable
fun NotificationPermissionDialog(
    type: NotificationPermissionDialogType,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val (title, message, confirmText) =
        when (type) {
            NotificationPermissionDialogType.TEMPORARY_DENIED ->
                Triple(
                    stringResource(R.string.notification_permission_title),
                    stringResource(R.string.notification_permission_message),
                    stringResource(R.string.try_again)
                )

            NotificationPermissionDialogType.PERMANENT_DENIED ->
                Triple(
                    stringResource(R.string.enable_notifications),
                    stringResource(R.string.notification_disabled_message),
                    stringResource(R.string.open_settings)
                )
        }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
