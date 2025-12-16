package com.abdelrahman.raafat.memento.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Custom Text Styles
object AppTextStyles {
    val MEMTypography = Typography()

    private val defaultTextStyle =
        TextStyle(
            lineHeight = 20.sp,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start
        )

    val textStyle16SPMedium =
        defaultTextStyle.copy(
            fontSize = 16.sp
        )

    val textStyle24SPBold =
        defaultTextStyle.copy(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

    val textStyle28SPMedium =
        defaultTextStyle.copy(
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 25.sp
        )
}