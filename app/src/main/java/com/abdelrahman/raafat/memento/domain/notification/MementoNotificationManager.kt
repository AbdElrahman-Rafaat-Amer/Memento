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
import com.abdelrahman.raafat.memento.domain.snooze.SnoozeOption
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MementoNotificationManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val notificationManager: NotificationManager
    ) {
        fun create() {
            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    channelImportance
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
            val builder =
                NotificationCompat
                    .Builder(
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
                            createDelayAction(
                                reminderId = id,
                                reminderName = title,
                                description = description,
                                delayMinutes = SnoozeOption.MIN_5.duration
                            )
                        )
                        addAction(
                            createDelayAction(
                                reminderId = id,
                                reminderName = title,
                                description = description,
                                delayMinutes = SnoozeOption.MIN_10.duration
                            )
                        )
                        addAction(
                            createDelayAction(
                                reminderId = id,
                                reminderName = title,
                                description = description,
                                delayMinutes = SnoozeOption.MIN_30.duration
                            )
                        )
                    }

            val notification = builder.build()

            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                NotificationManagerCompat.from(context).apply {
                    notify(id.toInt(), notification)
                }
            }
        }

        private fun createDelayAction(
            reminderId: Long,
            reminderName: String,
            description: String,
            delayMinutes: Int
        ): NotificationCompat.Action {
            val snoozeIntent =
                Intent(context, SnoozeAlarmReceiver::class.java)
                    .apply {
                        putExtra(SnoozeAlarmReceiver.ID_EXTRA, reminderId)
                        putExtra(SnoozeAlarmReceiver.NAME_EXTRA, reminderName)
                        putExtra(SnoozeAlarmReceiver.DESCRIPTION_EXTRA, description)
                        putExtra(SnoozeAlarmReceiver.SNOOZE_MINUTES_EXTRA, delayMinutes)
                    }

            val snoozePendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    (reminderId.toInt() * 1000) + delayMinutes,
                    snoozeIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

            return NotificationCompat.Action
                .Builder(
                    android.R.drawable.ic_menu_recent_history,
                    "+$delayMinutes ${context.getString(R.string.min)}",
                    snoozePendingIntent
                ).build()
        }

        companion object {
            const val CHANNEL_ID = "memento_reminders"
            private const val CHANNEL_NAME = "Reminders"
            private const val CHANNEL_DESCRIPTION = "Reminder notifications"
        }
    }
