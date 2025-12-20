package com.abdelrahman.raafat.memento.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.core.theme.ThemesPreviews
import com.abdelrahman.raafat.memento.dashboard.model.DashboardReminderUi

@Composable
fun ReminderItem(item: DashboardReminderUi) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.title,
                style = AppTextStyles.textStyle20SPSemiBold.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            if (item.additionalInfo.isNotBlank()) {
                Text(
                    text = item.additionalInfo,
                    style = AppTextStyles.textStyle16SPNormal.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            Text(
                text = item.dateTime,
                style = AppTextStyles.textStyle12SPNormal.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_edit),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.edit)
            )

            Icon(
                painter = painterResource(R.drawable.ic_delete),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.delete)
            )
        }
    }
}


@ThemesPreviews
@Composable
private fun ReminderItemPreview() {
    MementoTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        ) {
            ReminderItem(
                item = DashboardReminderUi(
                    id = 1,
                    title = "title",
                    dateTime = "time",
                    additionalInfo = "additionalInfo",
                    isDone = true
                )
            )
        }

    }
}