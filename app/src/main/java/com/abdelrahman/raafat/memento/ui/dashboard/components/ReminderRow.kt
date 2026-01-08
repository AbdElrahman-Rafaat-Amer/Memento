package com.abdelrahman.raafat.memento.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abdelrahman.raafat.memento.R
import com.abdelrahman.raafat.memento.ui.core.theme.AppTextStyles
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.core.theme.ThemesPreviews
import com.abdelrahman.raafat.memento.ui.dashboard.model.DashboardListItem.DashboardReminderUi

@Composable
fun ReminderRow(
    item: DashboardReminderUi,
    modifier: Modifier = Modifier,
    onDoneClicked: (DashboardReminderUi) -> Unit = {},
    onEditClicked: (DashboardReminderUi) -> Unit = {},
    onDeleteClicked: (DashboardReminderUi) -> Unit = {}
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.title,
                style =
                    AppTextStyles.textStyle20SPSemiBold.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
            )

            if (item.additionalInfo.isNotBlank()) {
                Text(
                    text = item.additionalInfo,
                    style =
                        AppTextStyles.textStyle16SPNormal.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                )
            }

            Text(
                text = item.dateTime,
                style =
                    AppTextStyles.textStyle12SPNormal.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onDoneClicked(item) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = stringResource(R.string.done),
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(
                onClick = { onEditClicked(item) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = stringResource(R.string.edit),
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(
                onClick = { onDeleteClicked(item) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.delete),
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@ThemesPreviews
@Composable
private fun ReminderItemPreview() {
    MementoTheme {
        Box(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.background)
        ) {
            ReminderRow(
                item =
                    DashboardReminderUi(
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
