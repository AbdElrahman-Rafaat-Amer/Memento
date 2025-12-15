package com.abdelrhman.raafat.memento
import androidx.compose.runtime.Composable
import com.abdelrhman.raafat.memento.ui.theme.MementoTheme
import com.abdelrhman.raafat.memento.ui.theme.ThemesPreviews


@Suppress("FunctionName")
@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,
) {

}


@ThemesPreviews
@Composable
fun OnboardingScreenPreview() {
    MementoTheme {
        OnboardingScreen(onFinished = {})
    }
}