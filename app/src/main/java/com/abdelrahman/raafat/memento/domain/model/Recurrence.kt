package com.abdelrahman.raafat.memento.domain.model

import com.abdelrahman.raafat.memento.R

enum class Recurrence(
    val displayNameRes: Int
) {
    NONE(R.string.does_not_repeat),
    DAILY(R.string.daily),
    WEEKLY(R.string.weekly),
    MONTHLY(R.string.monthly)
}
