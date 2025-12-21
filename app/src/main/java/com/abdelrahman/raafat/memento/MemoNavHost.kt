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
    startDestination: String = Dashboard.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        composable(route = Dashboard.route) {
            DashboardScreen{
                navController.navigate(AddReminder.route)
            }
        }

        composable(route = AddReminder.route) {
            AddReminderScreen{
                navController.navigateUp()
            }
        }

        composable(route = History.route) {
            //TODO History Screen
        }
    }
}