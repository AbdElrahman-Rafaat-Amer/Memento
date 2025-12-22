package com.abdelrahman.raafat.memento.ui.core.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes

// Themes
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Theme",
    group = "Themes"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Theme",
    group = "Themes"
)
annotation class ThemesPreviews

// Locales
@Preview(
    locale = "ar",
    name = "Arabic content",
    group = "Languages"
)
@Preview(
    locale = "en",
    name = "English content",
    group = "Languages"
)
annotation class LanguagesPreviews


// Themes
@ThemesPreviews
@PreviewDynamicColors
// Locales
@LanguagesPreviews
// Font scales
@PreviewFontScale
// Screen Sizes
@PreviewScreenSizes
annotation class AllPreviews
