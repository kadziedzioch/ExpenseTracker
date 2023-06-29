package com.example.expensetracker.feature_expense.domain.use_case

data class ExpenseUseCases (
    val addExpense: AddExpense,
    val deleteExpense: DeleteExpense,
    val getExpenses: GetExpenses,
    val getExpensesByDate: GetExpensesByDate,
    val getRecentExpenses: GetRecentExpenses
)