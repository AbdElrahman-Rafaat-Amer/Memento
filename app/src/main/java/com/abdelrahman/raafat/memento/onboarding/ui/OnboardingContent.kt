package com.abdelrahman.raafat.memento.onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.core.theme.ThemesPreviews
import com.abdelrahman.raafat.memento.onboarding.model.OnboardingItem

@Composable
fun OnboardingContent(
    onboardingItem: OnboardingItem,
    modifier: Modifier = Modifier
) {
    val windowInfo = LocalWindowInfo.current
    val screenHeight = with(LocalDensity.current) {
        windowInfo.containerSize.height.toDp()
    }

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(Modifier.height(screenHeight * 0.3f))

        Image(
            painter = painterResource(onboardingItem.imageResId),
            contentDescription = null,
            modifier = Modifier.size(260.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(onboardingItem.titleResId),
            style = AppTextStyles.textStyle24SPBold.copy(
                lineHeight = TextUnit.Unspecified,
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(onboardingItem.subtitleResId),
            style = AppTextStyles.textStyle16SPNormal.copy(
                lineHeight = 22.sp,
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@ThemesPreviews
@Composable
private fun OnboardingContentPreview() {
    MementoTheme {
        OnboardingContent(
            onboardingItem =
                OnboardingItem(
                    titleResId = R.string.onboard_title_4,
                    subtitleResId = R.string.onboard_subtitle_4,
                    imageResId = R.drawable.ic_onboard_4
                ),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}