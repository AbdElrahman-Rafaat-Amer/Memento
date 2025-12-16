package com.abdelrahman.raafat.memento.splash

sealed class Destination {
    data object Onboarding : Destination()
    data object Main : Destination()
}