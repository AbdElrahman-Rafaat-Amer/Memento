package com.abdelrhman.raafat.memento.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
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

    val textStyle10SPNormal =
        defaultTextStyle.copy(
            fontSize = 10.sp,
            lineHeight = 12.sp,
            fontWeight = FontWeight.Medium
        )

    val textStyle12SPNormal =
        defaultTextStyle.copy(
            fontSize = 12.sp,
            lineHeight = 16.sp
        )

    val textStyle13SPNormal =
        defaultTextStyle.copy(
            fontSize = 13.sp,
            lineHeight = 16.sp
        )

    val textStyle13SPMedium =
        textStyle13SPNormal.copy(
            fontWeight = FontWeight.Medium
        )

    val textStyle13SPNormalItalic = textStyle13SPNormal.copy(fontStyle = FontStyle.Italic)

    val textStyle14SPNormal =
        defaultTextStyle.copy(
            fontSize = 14.sp,
            lineHeight = 18.sp
        )

    val textStyle14SPNormalItalic =
        defaultTextStyle.copy(
            lineHeight = 17.sp,
            fontStyle = FontStyle.Italic
        )

    val textStyle15SPMedium =
        defaultTextStyle.copy(
            fontSize = 15.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.Medium
        )

    val textStyle16SPNormal =
        defaultTextStyle.copy(
            fontSize = 16.sp
        )

    val textStyle16SPMedium =
        textStyle16SPNormal.copy(
            fontWeight = FontWeight.Medium
        )

    val textStyle18SPNormal =
        defaultTextStyle.copy(
            fontSize = 18.sp,
            lineHeight = 25.sp
        )

    val textStyle18SPSemiBold =
        defaultTextStyle.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 25.sp
        )

    val textStyle18SPBold =
        textStyle18SPSemiBold.copy(
            fontWeight = FontWeight.Bold
        )

    val textStyle21SPNormal =
        defaultTextStyle.copy(
            fontSize = 21.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 22.sp
        )

    val textStyle21SPBold =
        defaultTextStyle.copy(
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 25.sp
        )

    val textStyle28SPMedium =
        defaultTextStyle.copy(
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 25.sp
        )

    val textStyle64SPSemiBold =
        defaultTextStyle.copy(
            fontSize = 64.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 77.sp
        )
}