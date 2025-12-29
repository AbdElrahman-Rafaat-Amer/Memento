package com.abdelrahman.raafat.memento.di

import android.app.NotificationManager
import android.content.Context
import com.abdelrahman.raafat.memento.domain.notification.ReminderNotificationScheduler
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
    fun provideNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideReminderScheduler(
        @ApplicationContext context: Context
    ): ReminderNotificationScheduler =
        ReminderNotificationScheduler(context)


}