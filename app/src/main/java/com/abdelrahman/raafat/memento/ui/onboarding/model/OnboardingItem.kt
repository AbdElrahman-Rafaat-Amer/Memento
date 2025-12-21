package com.abdelrahman.raafat.memento.ui.onboarding.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnboardingItem(
    @StringRes val titleResId: Int,
    @StringRes val subtitleResId: Int,
    @DrawableRes val imageResId: Int
)