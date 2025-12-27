package com.abdelrahman.raafat.memento

data object Dashboard {
    const val ROUTE: String = "Dashboard"
}

object ReminderEditorDestination {

    const val ARG_REMINDER_ID = "reminderId"

    const val ROUTE = "reminder_editor"
    const val ROUTE_WITH_ARG = "$ROUTE/{$ARG_REMINDER_ID}"

    fun createRoute(reminderId: Long): String =
        "$ROUTE/$reminderId"
}

