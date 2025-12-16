package com.abdelrhman.raafat.memento.splash

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
import com.abdelrhman.raafat.memento.R
import com.abdelrhman.raafat.memento.core.theme.AppTextStyles
import com.abdelrhman.raafat.memento.core.theme.MementoTheme
import com.abdelrhman.raafat.memento.core.theme.ThemesPreviews
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.memento_splash)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        speed = 1f
    )

    var visible by remember { mutableStateOf(false) }
    val durationMillis = 600
    val delayMillis =  400

    LaunchedEffect(key1 = progress) {
        if (progress == 1f) {
            delay(300)
            onFinished()
        }
    }

    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.6f)
        )

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
            ) + fadeIn(animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis))
        ) {
            Text(
                text = stringResource(R.string.splash_title),
                style = AppTextStyles.textStyle24SPBold.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
            ) + fadeIn(animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis))
        ) {
            Text(
                text = stringResource(R.string.splash_subtitle),
                style = AppTextStyles.textStyle16SPMedium.copy(
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
