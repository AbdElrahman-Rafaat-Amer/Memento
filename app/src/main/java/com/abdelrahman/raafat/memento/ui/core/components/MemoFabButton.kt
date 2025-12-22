package com.abdelrahman.raafat.memento.ui.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews

@Composable
fun MemoFabButton(
    @DrawableRes iconResID: Int,
    contentDescription: String,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    fabColor: Color = MaterialTheme.colorScheme.primary,
    iconTintColor: Color = MaterialTheme.colorScheme.onSurface
) {
    FloatingActionButton(
        onClick = onFabClick,
        modifier = modifier,
        shape = CircleShape,
        containerColor = fabColor,
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        content = {
            Icon(
                painter = painterResource(iconResID),
                contentDescription = contentDescription,
                modifier = iconModifier,
                tint = iconTintColor,
            )
        }
    )
}

@ThemesPreviews
@Composable
private fun MemoFabButtonPreview() {
    MementoTheme {
        MemoFabButton(
            iconResID = android.R.drawable.ic_menu_add,
            contentDescription = "Add",
            onFabClick = {}
        )
    }
}