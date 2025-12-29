package com.abdelrahman.raafat.memento.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE

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
}