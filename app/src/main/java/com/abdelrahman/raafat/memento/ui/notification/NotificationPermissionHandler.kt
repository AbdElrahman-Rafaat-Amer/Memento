package com.abdelrahman.raafat.memento.ui.notification

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationPermissionHandler(
    onPermissionGranted: () -> Unit,
    openAppSettings: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    var dialogType by remember {
        mutableStateOf<NotificationPermissionDialogType?>(null)
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                onPermissionGranted()
            } else {
                val shouldShowRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.POST_NOTIFICATIONS
                    )

                dialogType =
                    if (shouldShowRationale) {
                        NotificationPermissionDialogType.TEMPORARY_DENIED
                    } else {
                        NotificationPermissionDialogType.PERMANENT_DENIED
                    }
            }
        }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    dialogType?.let { type ->
        NotificationPermissionDialog(
            type = type,
            onDismiss = { dialogType = null },
            onConfirm = {
                dialogType = null
                when (type) {
                    NotificationPermissionDialogType.TEMPORARY_DENIED ->
                        permissionLauncher.launch(
                            Manifest.permission.POST_NOTIFICATIONS
                        )

                    NotificationPermissionDialogType.PERMANENT_DENIED ->
                        openAppSettings()
                }
            }
        )
    }
}
