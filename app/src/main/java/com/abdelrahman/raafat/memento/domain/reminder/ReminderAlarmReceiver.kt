package com.abdelrahman.raafat.memento.domain.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.abdelrahman.raafat.memento.domain.notification.MementoNotificationManager
import com.abdelrahman.raafat.memento.domain.repository.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReminderAlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationManager: MementoNotificationManager

    @Inject
    lateinit var repository: ReminderRepository
    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getLongExtra(ID_EXTRA, INVALID_REMINDER_ID)
        val reminderName = intent.getStringExtra(NAME_EXTRA) ?: "Reminder"
        val reminderDescription =
            intent.getStringExtra(DESCRIPTION_EXTRA) ?: "Reminder Description"

        // Show notification only when reminder id is valid
        if (reminderId == INVALID_REMINDER_ID) {
            return
        }
        notificationManager.showNotification(
            id = reminderId,
            title = reminderName,
            description = reminderDescription
        )

        val pendingResult: PendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.clearSnooze(reminderId)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to clear snooze for reminder: $reminderId", exception)
            } finally {
                pendingResult.finish()
            }
        }
    }

    companion object {
        const val ID_EXTRA = "ID_EXTRA"
        const val NAME_EXTRA = "NAME_EXTRA"
        const val DESCRIPTION_EXTRA = "DESCRIPTION_EXTRA"
        private const val INVALID_REMINDER_ID = -1L
        private const val TAG = "ReminderAlarmReceiver"
    }
}