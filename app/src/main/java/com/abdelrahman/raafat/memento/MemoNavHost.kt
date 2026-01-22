package com.abdelrahman.raafat.memento

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abdelrahman.raafat.memento.ui.dashboard.DashboardScreen
import com.abdelrahman.raafat.memento.ui.history.HistoryScreen
import com.abdelrahman.raafat.memento.ui.remindereditor.AddReminderScreen

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
                    navController.navigate(ReminderEditorDestination.ROUTE)
                },
                onUpdateClicked = {
                    navController.navigate(ReminderEditorDestination.createRoute(it.id))
                },
                onHistoryClicked = {
                    navController.navigate(History.ROUTE)
                }
            )
        }

        composable(route = ReminderEditorDestination.ROUTE) {
            AddReminderScreen {
                navController.navigateUp()
            }
        }

        composable(
            route = ReminderEditorDestination.ROUTE_WITH_ARG,
            arguments =
                listOf(
                    navArgument(ReminderEditorDestination.ARG_REMINDER_ID) {
                        type = NavType.LongType
                    }
                )
        ) {
            AddReminderScreen(
                onBack = { navController.navigateUp() }
            )
        }

        // History Screen
        composable(History.ROUTE) {
            HistoryScreen(
                onBack = { navController.navigateUp() }
            )
        }
    }
}
