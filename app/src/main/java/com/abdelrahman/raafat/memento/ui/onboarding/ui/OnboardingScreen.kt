package com.abdelrahman.raafat.memento.ui.onboarding.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.core.components.MEMCheckbox
import com.abdelrahman.raafat.memento.ui.core.components.MEMPrimaryButton
import com.abdelrahman.raafat.memento.ui.core.components.MEMProgressIndicator
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews
import com.abdelrahman.raafat.memento.ui.onboarding.OnboardingViewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.wrapContentHeight(),
    ) {
        val onboardingList = viewModel.getOnboardingItems()
        val next = stringResource(R.string.next)
        val start = stringResource(R.string.get_started)
        val primaryButtonText = remember { mutableStateOf((next)) }

        var isLastItem by remember { mutableStateOf(false) }

        val pagerState =
            rememberPagerState(
                initialPage = 0,
                pageCount = {
                    onboardingList.size
                }
            )


        val coroutineScope = rememberCoroutineScope()

        // Listen for changes in the current page
        LaunchedEffect(pagerState.currentPage) {
            if (pagerState.currentPage >= onboardingList.size - 1) {
                primaryButtonText.value = start
                isLastItem = true
            } else {
                primaryButtonText.value = next
                isLastItem = false
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) { page ->
            OnboardingContent(
                onboardingItem = onboardingList[page],
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(16.dp))

        MEMProgressIndicator(
            pagerState = pagerState,
            size = onboardingList.size,
            currentPage = 0
        )

        Spacer(Modifier.height(16.dp))

        MEMPrimaryButton(
            text = primaryButtonText.value,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            if (pagerState.currentPage == onboardingList.size - 1) {
                viewModel.saveUserChoice(viewModel.showAgain)
                onFinished.invoke()
            } else {
                coroutineScope.launch {
                    val nextPageIndex = (pagerState.currentPage + 1)
                        .coerceAtMost(onboardingList.size - 1)
                    pagerState.animateScrollToPage(nextPageIndex)
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        if (isLastItem) {
            MEMCheckbox(stringResource(R.string.show_onboarding)) {
                viewModel.showAgain = it
            }
        } else {
            MEMPrimaryButton(
                text = stringResource(R.string.skip),
                isTextButton = true,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                coroutineScope.launch {
                    val lastItemIndex = onboardingList.size - 1
                    pagerState.animateScrollToPage(lastItemIndex)
                }
            }
        }
    }
}

@ThemesPreviews
@Composable
private fun OnboardingScreenPreview() {
    MementoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            OnboardingScreen(onFinished = {})
        }
    }
}