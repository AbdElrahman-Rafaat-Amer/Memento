package com.abdelrahman.raafat.memento.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abdelrahman.raafat.memento.R

object MementoNotificationChannel {

    const val CHANNEL_ID = "memento_reminders"

    fun create(context: Context) {
        val channelName = "Reminders"
        val channelDescription = "Reminder notifications"
        val channelImportance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(CHANNEL_ID, channelName, channelImportance)
        channel.description = channelDescription
        channel.setShowBadge(true)
        channel.enableLights(true)
        channel.enableVibration(true)

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun delete(context: Context) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.deleteNotificationChannel(CHANNEL_ID)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(
        context: Context,
        reminderId: Long,
        reminderName: String,
        reminderDescription: String
    ) {
        val builder = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        ).apply {
            setSmallIcon(R.drawable.ic_memento)
            setContentTitle(reminderName)
            if (reminderDescription.isNotEmpty()) {
                setContentText(reminderDescription)
            }
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setCategory(NotificationCompat.CATEGORY_REMINDER)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setAutoCancel(true)
        }

        val notification = builder.build()

        NotificationManagerCompat.from(context).apply {
            notify(reminderId.toInt(), notification)
        }
    }
}