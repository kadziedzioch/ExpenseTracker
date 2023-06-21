package com.example.expensetracker.feature_expense.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(var title:String, var imageVector: ImageVector, var screen_route:String){
    object HomeScreen: Screen("Home", Icons.Default.Home, "home_screen")
    object HistoryScreen: Screen("History", Icons.Default.DateRange, "history_screen")
    object AddExpenseScreen: Screen("Add Expense", Icons.Default.Add, "add_expense_screen")
}