package com.abdelrahman.raafat.memento.domain.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.abdelrahman.raafat.memento.domain.repository.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SnoozeAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var scheduler: ReminderNotificationScheduler
    @Inject
    lateinit var repository: ReminderRepository

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getLongExtra("reminderId", -1L)
        val snoozeMinutes = intent.getIntExtra("snoozeMinutes", 10)

        //TO handle snooze later

    }

    companion object {
        const val ID_EXTRA = "ID_EXTRA"
        const val SNOOZE_MINUTES_EXTRA = "SNOOZE_MINUTES_EXTRA"
    }
}
