package com.example.expensetracker.feature_expense.presentation.home

import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.use_case.GetRecentExpenses
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import java.time.LocalDate

data class HomeState(
    val expenses : Map<ExpenseCategory,List<Expense>> = emptyMap(),
    val recentExpenses: List<Expense> = emptyList(),
    val date: LocalDate = LocalDate.now().withDayOfMonth(1),
    val finalSum: Long = 0
)