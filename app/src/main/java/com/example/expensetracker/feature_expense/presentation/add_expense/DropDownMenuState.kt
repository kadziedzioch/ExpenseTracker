package com.example.expensetracker.feature_expense.presentation.add_expense

import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory

data class DropDownMenuState(
    val selectedCategory : ExpenseCategory = ExpenseCategory.Home,
    val isExpanded : Boolean = false
)