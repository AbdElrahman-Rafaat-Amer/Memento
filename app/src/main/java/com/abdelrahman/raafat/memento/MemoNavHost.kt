package com.abdelrahman.raafat.memento

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdelrahman.raafat.memento.ui.addreminder.AddReminderScreen
import com.abdelrahman.raafat.memento.ui.dashboard.DashboardScreen

@Composable
fun MemoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Dashboard.ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Dashboard.ROUTE) {
            DashboardScreen(
                onAddClicked = {
                    navController.navigate(AddReminder.ROUTE)
                },
                onUpdateClicked = {
                    navController.navigate(UpdateReminder.ROUTE)
                }
            )
        }

        composable(route = AddReminder.ROUTE) {
            AddReminderScreen {
                navController.navigateUp()
            }
        }

        composable(route = UpdateReminder.ROUTE) {
            //TODO UpdateReminder Screen
        }
    }
}