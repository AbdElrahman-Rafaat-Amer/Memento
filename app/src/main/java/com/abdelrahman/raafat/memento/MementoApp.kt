package com.abdelrahman.raafat.memento

import android.app.Application
import com.abdelrahman.raafat.memento.domain.notification.MementoNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MementoApp : Application() {

    @Inject
    lateinit var notificationManager: MementoNotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager.create()
    }
}
