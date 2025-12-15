package com.abdelrhman.raafat.memento.onboarding.ui
import androidx.compose.runtime.Composable
import com.abdelrhman.raafat.memento.core.theme.MementoTheme
import com.abdelrhman.raafat.memento.core.theme.ThemesPreviews


@Suppress("FunctionName")
@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,
) {

}


@ThemesPreviews
@Composable
private fun OnboardingScreenPreview() {
    MementoTheme {
        OnboardingScreen(onFinished = {})
    }
}
