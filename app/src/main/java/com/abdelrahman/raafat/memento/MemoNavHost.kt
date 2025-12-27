package com.abdelrahman.raafat.memento

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abdelrahman.raafat.memento.ui.remindereditor.AddReminderScreen
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
                    navController.navigate("Update_Reminder/${it.id}")
                }
            )
        }

        composable(route = AddReminder.ROUTE) {
            AddReminderScreen {
                navController.navigateUp()
            }
        }

        composable(
            route = UpdateReminder.ROUTE,
            arguments = listOf(
                navArgument("reminderId") {
                    type = NavType.LongType
                }
            )
        ) {
            AddReminderScreen(
                onBack = { navController.navigateUp() }
            )
        }
    }
}