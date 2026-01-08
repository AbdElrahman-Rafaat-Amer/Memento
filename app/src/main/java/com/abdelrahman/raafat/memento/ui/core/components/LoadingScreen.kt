package com.abdelrahman.raafat.memento.ui.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews
import com.airbnb.lottie.compose.LottieConstants

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    val windowInfo = LocalWindowInfo.current
    val screenHeight =
        with(LocalDensity.current) {
            windowInfo.containerSize.height.toDp()
        }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieLoader(
            modifier =
                Modifier
                    .height(
                        height = screenHeight * 0.3f
                    ),
            lottieAnimation = R.raw.loader,
            iterations = LottieConstants.IterateForever
        )
    }
}

@ThemesPreviews
@Composable
private fun LoadingScreenPreview() {
    MementoTheme {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            LoadingScreen()
        }
    }
}
