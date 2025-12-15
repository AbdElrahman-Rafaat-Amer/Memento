package com.abdelrhman.raafat.memento.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abdelrhman.raafat.memento.core.theme.ThemesPreviews
import com.abdelrhman.raafat.memento.core.theme.MementoTheme

@Composable
fun MEMProgressIndicator(
    size: Int,
    currentPage: Int? = null,
    pagerState: PagerState? = null
) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(size) { iteration ->
            val isSelected =
                if (pagerState != null) {
                    pagerState.currentPage % size == iteration
                } else {
                    currentPage == iteration
                }

            val color =
                if (isSelected) {
                    Color.Transparent
                } else {
                    MaterialTheme.colorScheme.primary
                }

            val borderColor =
                if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                }
            MEMCustomIndicator(borderColor, color)
        }
    }
}

@ThemesPreviews
@Composable
private fun MEMProgressIndicatorPreview() {
    MementoTheme {
        MEMProgressIndicator(
            size = 4,
            currentPage = 3,
            pagerState = null
        )
    }
}