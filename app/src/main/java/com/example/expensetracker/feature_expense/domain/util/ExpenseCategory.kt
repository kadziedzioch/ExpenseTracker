package com.example.expensetracker.feature_expense.domain.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class ExpenseCategory(val icon: ImageVector){
    Health(Icons.Outlined.HealthAndSafety),
    Home(Icons.Outlined.Home),
    Transport(Icons.Outlined.ElectricCar),
    Food(Icons.Outlined.Fastfood),
    Education(Icons.Outlined.Book),
    Utilities(Icons.Outlined.Power),
    Clothing(Icons.Outlined.ShoppingBag),
    Other(Icons.Outlined.MoreHoriz)
}