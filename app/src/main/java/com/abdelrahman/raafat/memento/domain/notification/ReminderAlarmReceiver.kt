package com.abdelrahman.raafat.memento.domain.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.abdelrahman.raafat.memento.notification.MementoNotificationChannel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderAlarmReceiver : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getLongExtra(REMINDER_ID_EXTRA, -1)
        val reminderName = intent.getStringExtra(REMINDER_NAME_EXTRA) ?: "Reminder"
        val reminderDescription =
            intent.getStringExtra(REMINDER_DESCRIPTION_EXTRA) ?: "Reminder Description"

        MementoNotificationChannel.showNotification(
            context,
            reminderId,
            reminderName,
            reminderDescription
        )
    }

    companion object{
        const val REMINDER_ID_EXTRA = "REMINDER_ID_EXTRA"
        const val REMINDER_NAME_EXTRA = "REMINDER_NAME_EXTRA"
        const val REMINDER_DESCRIPTION_EXTRA = "REMINDER_DESCRIPTION_EXTRA"

    }
}