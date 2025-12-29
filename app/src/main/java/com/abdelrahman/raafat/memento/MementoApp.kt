package com.abdelrahman.raafat.memento

import android.app.Application
import com.abdelrahman.raafat.memento.notification.MementoNotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MementoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MementoNotificationChannel.create(this)
    }
}
