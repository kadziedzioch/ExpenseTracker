package com.example.expensetracker.feature_expense.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.feature_expense.presentation.add_expense.AddExpenseScreen
import com.example.expensetracker.feature_expense.presentation.history.HistoryScreen
import com.example.expensetracker.feature_expense.presentation.home.HomeScreen
import com.example.expensetracker.feature_expense.presentation.shared.DefaultBottomNavigation
import com.example.expensetracker.feature_expense.presentation.util.Screen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    bottomBar = { DefaultBottomNavigation(navController = navController) },
                    scaffoldState = scaffoldState
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.screen_route
                    ){
                        composable(Screen.HomeScreen.screen_route){
                            HomeScreen(
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                        composable(Screen.HistoryScreen.screen_route){
                            HistoryScreen(
                                modifier = Modifier.padding(paddingValues),
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Screen.AddExpenseScreen.screen_route){
                            AddExpenseScreen(
                                modifier = Modifier.padding(paddingValues),
                                navController = navController,
                                scaffoldState = scaffoldState
                            )
                        }
                    }
                }


            }
        }
    }
}

