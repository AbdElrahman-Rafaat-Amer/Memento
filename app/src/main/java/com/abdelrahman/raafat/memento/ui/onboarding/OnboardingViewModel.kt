package com.abdelrahman.raafat.memento.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.data.local.datastore.OnboardingPreferences
import com.abdelrahman.raafat.memento.ui.onboarding.model.OnboardingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
    @Inject
    constructor(
        private val onboardingPreferences: OnboardingPreferences
    ) : ViewModel() {
        var showAgain = true

        fun saveUserChoice(showAgain: Boolean) {
            viewModelScope.launch {
                onboardingPreferences.setShowOnboarding(showAgain)
            }
        }

        fun getOnboardingItems(): List<OnboardingItem> =
            listOf(
                OnboardingItem(
                    titleResId = R.string.onboard_title_1,
                    subtitleResId = R.string.onboard_subtitle_1,
                    imageResId = R.drawable.ic_onboard_1
                ),
                OnboardingItem(
                    titleResId = R.string.onboard_title_2,
                    subtitleResId = R.string.onboard_subtitle_2,
                    imageResId = R.drawable.ic_onboard_2
                ),
                OnboardingItem(
                    titleResId = R.string.onboard_title_3,
                    subtitleResId = R.string.onboard_subtitle_3,
                    imageResId = R.drawable.ic_onboard_3
                ),
                OnboardingItem(
                    titleResId = R.string.onboard_title_4,
                    subtitleResId = R.string.onboard_subtitle_4,
                    imageResId = R.drawable.ic_onboard_4
                )
            )
    }
