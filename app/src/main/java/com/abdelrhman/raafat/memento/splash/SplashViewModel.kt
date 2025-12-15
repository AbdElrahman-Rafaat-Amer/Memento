package com.abdelrhman.raafat.memento.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrhman.raafat.memento.data.local.datastore.OnboardingPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    onboardingPreferences: OnboardingPreferences
) : ViewModel() {

    val startDestination = onboardingPreferences.showOnboarding
        .map { show ->
            if (show) Destination.Onboarding else Destination.Main
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            Destination.Onboarding
        )
}
