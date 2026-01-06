package com.abdelrahman.raafat.memento.domain.reminder

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.abdelrahman.raafat.memento.domain.repository.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SnoozeAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var scheduler: ReminderNotificationScheduler

    @Inject
    lateinit var repository: ReminderRepository

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getLongExtra(ID_EXTRA, -1L)
        val reminderName = intent.getStringExtra(NAME_EXTRA) ?: ""
        val reminderDescription = intent.getStringExtra(DESCRIPTION_EXTRA) ?: ""
        val snoozeMinutes = intent.getIntExtra(SNOOZE_MINUTES_EXTRA, 10)

        // Cancel current notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(reminderId.toInt())

        // Schedule new reminder
        val newTriggerTime = System.currentTimeMillis() + (snoozeMinutes * 60 * 1000)

        scheduler.scheduleReminder(
            reminderId = reminderId,
            triggerAtMillis = newTriggerTime,
            title = reminderName,
            additionalInfo = reminderDescription
        )

        val pendingResult: PendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.markAsSnoozed(reminderId, newTriggerTime)
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to mark as snoozed for reminder: $reminderId", exception)
            } finally {
                pendingResult.finish()
            }
        }

    }

    companion object {
        const val ID_EXTRA = "ID_EXTRA"
        const val NAME_EXTRA = "NAME_EXTRA"
        const val DESCRIPTION_EXTRA = "DESCRIPTION_EXTRA"
        const val SNOOZE_MINUTES_EXTRA = "SNOOZE_MINUTES_EXTRA"
        private const val TAG = "SnoozeAlarmReceiver"
    }
}
