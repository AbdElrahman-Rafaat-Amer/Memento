package com.abdelrhman.raafat.memento.onboarding.model

import androidx.annotation.DrawableRes

data class OnboardingItem(
    val title: String,
    val subtitle: String,
    @DrawableRes val imageResId: Int
)