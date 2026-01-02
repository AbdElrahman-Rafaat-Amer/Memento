package com.abdelrahman.raafat.memento.domain.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.domain.reminder.SnoozeAlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MementoNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) {

    fun create() {
        val channelImportance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME, channelImportance
        ).apply {
            description = CHANNEL_DESCRIPTION
            setShowBadge(true)
            enableLights(true)
            enableVibration(true)
        }

        notificationManager.createNotificationChannel(channel)
    }

    fun delete() {
        notificationManager.deleteNotificationChannel(CHANNEL_ID)
    }

    fun showNotification(
        id: Long,
        title: String,
        description: String
    ) {

        val snoozeIntent = Intent(context, SnoozeAlarmReceiver::class.java).apply {
            putExtra(SnoozeAlarmReceiver.ID_EXTRA, id)
            putExtra(SnoozeAlarmReceiver.SNOOZE_MINUTES_EXTRA, 10)
        }

        val snoozePendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        ).apply {
            setSmallIcon(R.drawable.ic_memento)
            setContentTitle(title)
            if (description.isNotEmpty()) {
                setContentText(description)
            }
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setCategory(NotificationCompat.CATEGORY_REMINDER)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setAutoCancel(true)
            addAction(
                R.drawable.ic_onboard_2,
                context.getString(R.string.snooze),
                snoozePendingIntent
            )
        }

        val notification = builder.build()

        if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            NotificationManagerCompat.from(context).apply {
                notify(id.toInt(), notification)
            }
        }
    }

    companion object {
        const val CHANNEL_ID = "memento_reminders"
        private const val CHANNEL_NAME = "Reminders"
        private const val CHANNEL_DESCRIPTION = "Reminder notifications"
    }
}