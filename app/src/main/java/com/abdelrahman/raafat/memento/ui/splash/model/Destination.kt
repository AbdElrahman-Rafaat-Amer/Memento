package com.abdelrahman.raafat.memento.ui.splash.model

sealed class Destination {
    data object Onboarding : Destination()
    data object Main : Destination()
}