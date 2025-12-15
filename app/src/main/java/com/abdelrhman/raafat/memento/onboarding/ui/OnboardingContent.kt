package com.abdelrhman.raafat.memento.onboarding.ui

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdelrhman.raafat.memento.R
import com.abdelrhman.raafat.memento.core.theme.AppTextStyles
import com.abdelrhman.raafat.memento.core.theme.MementoTheme
import com.abdelrhman.raafat.memento.core.theme.ThemesPreviews
import com.abdelrhman.raafat.memento.onboarding.model.OnboardingItem

@Composable
fun OnboardingContent(
    onboardingItem: OnboardingItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(onboardingItem.imageResId),
            contentDescription = null,
            modifier = Modifier.size(260.dp)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = onboardingItem.title,
            style = AppTextStyles.textStyle24SPBold.copy(
                lineHeight = TextUnit.Unspecified,
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = onboardingItem.subtitle,
            style = AppTextStyles.textStyle16SPMedium.copy(
                lineHeight = 22.sp,
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 24.dp)
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
                    title = stringResource(R.string.onboard_title_4),
                    subtitle = stringResource(R.string.onboard_subtitle_4),
                    imageResId = R.drawable.ic_onboard_4
                ),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}