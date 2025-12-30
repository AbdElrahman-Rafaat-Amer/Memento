package com.abdelrahman.raafat.memento.domain.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.abdelrahman.raafat.memento.domain.notification.MementoNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderAlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationManager: MementoNotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getLongExtra(ID_EXTRA, -1)
        val reminderName = intent.getStringExtra(NAME_EXTRA) ?: "Reminder"
        val reminderDescription =
            intent.getStringExtra(DESCRIPTION_EXTRA) ?: "Reminder Description"

        // Show notification only when reminder id is valid
        if (reminderId != -1L) {
            notificationManager.showNotification(
                id = reminderId,
                title = reminderName,
                description = reminderDescription
            )
        }
    }

    companion object {
        const val ID_EXTRA = "ID_EXTRA"
        const val NAME_EXTRA = "NAME_EXTRA"
        const val DESCRIPTION_EXTRA = "DESCRIPTION_EXTRA"

    }
}