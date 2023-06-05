package com.example.expensetracker.feature_expense.presentation.home

import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory
import java.time.LocalDate

data class HomeState(
    val expenses : Map<ExpenseCategory,List<Expense>> = emptyMap(),
    val date: Long = LocalDate.now().withDayOfMonth(1).toEpochDay(),
    val finalSum: Long = 0
    )