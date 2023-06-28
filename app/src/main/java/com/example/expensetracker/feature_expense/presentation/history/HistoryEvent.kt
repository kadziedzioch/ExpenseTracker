package com.example.expensetracker.feature_expense.presentation.history

import com.example.expensetracker.feature_expense.domain.model.Expense
import com.example.expensetracker.feature_expense.domain.util.ExpenseCategory

sealed class HistoryEvent {
    data class SearchedText(val text: String) : HistoryEvent()
    data class CategoryClicked(val expenseCategory: ExpenseCategory) : HistoryEvent()
    data class ExpenseDeleted(val expense: Expense) : HistoryEvent()
    object RestoreExpense : HistoryEvent()
}