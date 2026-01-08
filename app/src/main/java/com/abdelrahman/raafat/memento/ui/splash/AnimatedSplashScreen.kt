package com.abdelrahman.raafat.memento.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.core.components.LottieLoader
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews

@Composable
fun AnimatedSplashScreen(
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    val durationMillis = 600
    val delayMillis = 400

    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieLoader(
            modifier =
                Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.6f),
            lottieAnimation = R.raw.memento_splash,
            onFinished = onFinished
        )

        AnimatedVisibility(
            visible = visible,
            enter =
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
                ) + fadeIn(animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis))
        ) {
            Text(
                text = stringResource(R.string.splash_title),
                style =
                    AppTextStyles.textStyle24SPBold.copy(
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
            )
        }

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(
            visible = visible,
            enter =
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
                ) + fadeIn(animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis))
        ) {
            Text(
                text = stringResource(R.string.splash_subtitle),
                style =
                    AppTextStyles.textStyle16SPNormal.copy(
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
            )
        }
    }
}

@ThemesPreviews
@Composable
private fun AnimatedSplashScreenPreview() {
    MementoTheme {
        AnimatedSplashScreen(
            {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
