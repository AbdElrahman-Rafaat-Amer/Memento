package com.abdelrahman.raafat.memento.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import com.abdelrahman.raafat.memento.domain.reminder.ReminderNotificationScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotificationModule {
    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager = context.getSystemService(NotificationManager::class.java)

    @Provides
    @Singleton
    fun provideReminderScheduler(
        @ApplicationContext context: Context,
        alarmManager: AlarmManager
    ): ReminderNotificationScheduler = ReminderNotificationScheduler(context = context, alarmManager = alarmManager)
}
