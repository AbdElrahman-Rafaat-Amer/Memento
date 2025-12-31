package com.abdelrahman.raafat.memento.domain.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abdelrahman.raafat.memento.R
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