package com.abdelrahman.raafat.memento.domain.result

sealed interface ReminderScheduleResult {
    data object Success : ReminderScheduleResult

    data class PastTrigger(
        val message: String?
    ) : ReminderScheduleResult

    data class ExactAlarmPermissionMissing(
        val message: String?
    ) : ReminderScheduleResult

    data class DataBaseError(
        val message: String?
    ) : ReminderScheduleResult

    data class UnknownError(
        val message: String?
    ) : ReminderScheduleResult
}
