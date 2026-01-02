package com.abdelrahman.raafat.memento.domain.snooze

import java.time.Duration

enum class SnoozeOption(val duration: Duration) {
    MIN_5(Duration.ofMinutes(5)),
    MIN_10(Duration.ofMinutes(10)),
    MIN_30(Duration.ofMinutes(30))
}
