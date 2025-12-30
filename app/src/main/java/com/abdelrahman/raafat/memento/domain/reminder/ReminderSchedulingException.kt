package com.abdelrahman.raafat.memento.domain.reminder

sealed class ReminderSchedulingException(message: String) : Exception(message)

class PastTriggerException :
    ReminderSchedulingException("Trigger time cannot be in the past")

class ExactAlarmPermissionException :
    ReminderSchedulingException("Exact alarm permission is not granted")
