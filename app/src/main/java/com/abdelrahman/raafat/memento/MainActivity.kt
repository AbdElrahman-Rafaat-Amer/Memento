package com.abdelrahman.raafat.memento

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.compose.rememberNavController
import com.abdelrahman.raafat.memento.notification.MementoNotificationChannel
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.notification.NotificationPermissionHandler
import com.abdelrahman.raafat.memento.ui.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                NotificationPermissionHandler(
                    onPermissionGranted = { showNotification() },
                    openAppSettings = { openAppSettings(applicationContext) }
                )
            }
            MementoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MementoApp(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }

    //TODO Just for testing will be removed later
    private fun showNotification() {
        // Create an explicit intent for an Activity in your app.
        val intent = Intent(this, OnboardingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(
            this,
            MementoNotificationChannel.CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_memento)
            .setContentTitle("Notification Title")
            .setContentText("Notification Text")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_check, getString(R.string.done), pendingIntent)
            .addAction(R.drawable.ic_delete, getString(R.string.delete), pendingIntent)
            .addAction(
                android.R.drawable.ic_input_add,
                getString(R.string.snooze),
                pendingIntent
            )
            .setAutoCancel(true)

        val notification = builder.build()

        NotificationManagerCompat.from(this).apply {
            notify(1, notification)
        }
    }
    
    fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}

@Composable
private fun MementoApp(modifier: Modifier) {
    val navController = rememberNavController()
    Box(
        modifier = modifier
    ) {
        MemoNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController
        )
    }
}