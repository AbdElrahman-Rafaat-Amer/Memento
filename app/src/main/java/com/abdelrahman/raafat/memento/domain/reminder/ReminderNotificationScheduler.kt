package com.abdelrahman.raafat.memento.domain.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReminderNotificationScheduler @Inject constructor(
    @ApplicationContext val context: Context,
    private val alarmManager: AlarmManager
) {
    fun scheduleReminder(
        reminderId: Long,
        triggerAtMillis: Long,
        title: String,
        additionalInfo: String
    ) {

        if (triggerAtMillis <= System.currentTimeMillis()) {
            throw PastTriggerException()
        }

        val intent = Intent(context, ReminderAlarmReceiver::class.java).apply {
            putExtra(ReminderAlarmReceiver.ID_EXTRA, reminderId)
            putExtra(ReminderAlarmReceiver.NAME_EXTRA, title)
            putExtra(ReminderAlarmReceiver.DESCRIPTION_EXTRA, additionalInfo)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderRequestCode(reminderId),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val canScheduleExactAlarms =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                alarmManager.canScheduleExactAlarms()
            } else {
                true
            }

        if (canScheduleExactAlarms) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        } else {
            throw ExactAlarmPermissionException()
        }

    }

    fun cancelReminder(reminderId: Long) {
        val intent = Intent(context, ReminderAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderRequestCode(reminderId),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    // Helper function to generate a unique request code for each reminder
    private fun reminderRequestCode(id: Long): Int =
        (id xor (id ushr 32)).toInt()

}