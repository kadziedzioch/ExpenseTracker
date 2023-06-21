package com.example.expensetracker.feature_expense.presentation.shared

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.expensetracker.feature_expense.presentation.util.Screen

@Composable
fun DefaultBottomNavigation(navController: NavController) {
    val items = listOf(
        Screen.HomeScreen,
        Screen.AddExpenseScreen,
        Screen.HistoryScreen
    )

    BottomNavigation(

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {Icon(imageVector = item.imageVector, contentDescription = item.title)},
                label = { Text(text = item.title, style = MaterialTheme.typography.caption) },
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}